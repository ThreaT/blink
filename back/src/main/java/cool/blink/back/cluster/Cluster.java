package cool.blink.back.cluster;

import cool.blink.back.cache.Etag;
import cool.blink.back.core.Blink;
import cool.blink.back.core.Fail;
import cool.blink.back.core.Http;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.exception.DuplicateNodeIdException;
import cool.blink.back.session.Session;
import cool.blink.back.utilities.HttpRequests;
import cool.blink.back.utilities.Sets;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;
import org.joda.time.DateTime;

/**
 * Ensure that all nodes have the same system time to prevent synchronization
 * problems.
 */
public class Cluster {

    private final Integer timeoutInMillis;
    private final List<Territory> exploredTerritories;
    private final List<Node> foundNodes;
    private final Set<Session> activeSessions;
    private final SocketHandler socketHandler;
    private final PortScanner portScanner;
    private final SessionManager sessionManager;
    private final DatabaseActionSynchronizer databaseActionSynchronizer;
    private final DatabaseActionExecutor databaseActionExecutor;
    private final HttpRequestHandler httpRequestHandler;

    public Cluster(Integer timeoutInMillis, Territory... exploredTerritories) throws IOException, ClassNotFoundException {
        this.timeoutInMillis = timeoutInMillis;
        this.exploredTerritories = Arrays.asList(exploredTerritories);
        this.foundNodes = Collections.synchronizedList(new ArrayList());
        this.activeSessions = Collections.synchronizedSet(new HashSet<>());
        this.httpRequestHandler = new HttpRequestHandler();
        this.httpRequestHandler.setPriority(Thread.MAX_PRIORITY);
        this.socketHandler = new SocketHandler();
        this.socketHandler.setPriority(Thread.MIN_PRIORITY);
        this.portScanner = new PortScanner();
        this.portScanner.setPriority(Thread.NORM_PRIORITY);
        this.sessionManager = new SessionManager();
        this.sessionManager.setPriority(Thread.MIN_PRIORITY);
        this.databaseActionSynchronizer = new DatabaseActionSynchronizer();
        this.databaseActionSynchronizer.setPriority(Thread.MIN_PRIORITY);
        this.databaseActionExecutor = new DatabaseActionExecutor();
        this.databaseActionExecutor.setPriority(Thread.MIN_PRIORITY);
    }

    /**
     * Used when making connections to other nodes on the cluster
     *
     * @return timeoutInMillis
     */
    public Integer getTimeoutInMillis() {
        return timeoutInMillis;
    }

    public List<Territory> getExploredTerritories() {
        return exploredTerritories;
    }

    public List<Node> getFoundNodes() {
        return foundNodes;
    }

    public Set<Session> getActiveSessions() {
        return activeSessions;
    }

    public HttpRequestHandler getHttpRequestHandler() {
        return httpRequestHandler;
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }

    public PortScanner getPortScanner() {
        return portScanner;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public DatabaseActionSynchronizer getDatabaseActionSynchronizer() {
        return databaseActionSynchronizer;
    }

    public DatabaseActionExecutor getDatabaseActionExecutor() {
        return databaseActionExecutor;
    }

    public class HttpRequestHandler extends Thread {

        @Override
        public void run() {
            while (true) {
                if (!Blink.getNode().getRequestQueue().isEmpty()) {
                    Request request = null;
                    if (Blink.getNode().getHandlingRequests()) {
                        try {
                            //1. Get request from queue
                            request = (Request) Blink.getNode().getRequestQueue().take();

                            //2. If request contains up to date etag for a supported scenario then send a 304
                            Etag requestEtag = Etag.getEtag(request);
                            if ((requestEtag != null) && (Etag.isScenarioSupported(requestEtag))) {
                                Blink.getWebServer().respond(request, new Response(Status.$304, ""));
                                continue;
                            }

                            //3. Find the best node to execute the request
                            Node bestNode = findBestNode(request);
                            if ((bestNode != null) && (!bestNode.equals(Blink.getNode()))) {
                                sendProxyHttpRequest(bestNode, request);
                                continue;
                            }

                            //4. If this node is the best node then find out which scenario should execute the request and execute it
                            if ((bestNode == null) || (bestNode.equals(Blink.getNode()))) {
                                Scenario execute = find(request);
                                if (execute != null) {
                                    run(execute, request);
                                    continue;
                                }
                            }

                            //5. If no node supports the request then send a redirect to the fail scenario
                            Response response = new Response(Status.$304, "");
                            response.getHeaders().put(Response.HeaderFieldName.Location, "/fail");
                            Blink.getWebServer().respond(request, response);
                        } catch (NullPointerException | IllegalAccessException | InstantiationException | InvocationTargetException | CloneNotSupportedException | IllegalArgumentException | NoSuchMethodException | SecurityException | IOException | InterruptedException ex) {
                            ///TODO replace (request.toString() != null ? request.toString() : "request was null") with null
                            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, (request.toString() != null ? request.toString() : "request was null"), ex);
                        }
                    } else {
                        Response response = new Response(Status.$304, "");
                        response.getHeaders().put(Response.HeaderFieldName.Location, "/fail");
                        Blink.getWebServer().respond(request, response);
                    }
                }
            }
        }

        public final synchronized Node findBestNode(Request request) {
            List<Node> suspects = Blink.getCluster().getFoundNodes();
            List<Node> candidates = new ArrayList<>();

            //1. Check if found nodes (it should find itself as well) supports a url to fulfill the request
            for (Node node_ : suspects) {
                for (Url url : node_.getSupportedUrls()) {
                    if (Url.hasMatchingAbsoluteUrls(url, request.getUrl())) {
                        candidates.add(node_);
                    }
                }
            }

            //2. Find least busy node that is currently handling requests
            Node bestNode = null;
            for (Node node_ : candidates) {
                if (((bestNode == null) || (bestNode.getRequestQueueSize() > node_.getRequestQueueSize())) && (node_.getHandlingRequests())) {
                    bestNode = node_;
                }
            }
            if (bestNode == null) {
                bestNode = Blink.getNode();
            }
            return bestNode;
        }

        /**
         * Loop through all existing application scenarios and return the one
         * that has intentions that match that of the request
         *
         * @param request request
         * @return Scenario Scenario
         * @throws IllegalAccessException When there is a problem invoking Fit
         * @throws IllegalArgumentException When there is a problem creating a
         * scenario clone
         * @throws InstantiationException When there is a problem invoking Fit
         * @throws InvocationTargetException When there is a problem invoking
         * Fit
         * @throws java.lang.CloneNotSupportedException When there is a problem
         * cloning the request scenario
         * @throws java.lang.NoSuchMethodException When a scenario cannot be
         * instantiated due to an invalid Class type or Intention
         */
        public Scenario find(Request request) throws IllegalAccessException, InstantiationException, InvocationTargetException, CloneNotSupportedException, IllegalArgumentException, NoSuchMethodException, SecurityException {
            for (Scenario scenario : Blink.getScenarios()) {
                if (isFit(scenario, request)) {
                    return scenario.clone();
                }
            }
            return null;
        }

        /**
         * @param scenario scenario
         * @param request request
         * @return Boolean true if the request matches the given scenario
         * @throws IllegalAccessException When there is a problem invoking Fit
         * @throws IllegalArgumentException When there is a problem creating a
         * scenario clone
         * @throws InstantiationException When there is a problem invoking Fit
         * @throws InvocationTargetException When there is a problem invoking
         * Fit
         * @throws java.lang.NoSuchMethodException When a scenario cannot be
         * instantiated due to an invalid Class type or Intention
         */
        public Boolean isFit(Scenario scenario, Request request) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
            Boolean fit;
            if (scenario instanceof Fail) {
                fit = (Boolean) scenario.getFit().getMethod().invoke(Blink.getFail(), new Object[]{request});
            } else {
                fit = (Boolean) scenario.getFit().getMethod().invoke(scenario, new Object[]{request});
            }
            return fit;
        }

        /**
         * If test mode is requested then run test method instead of main method
         *
         * @param scenario Scenario
         * @param request request
         * @throws IllegalAccessException When there is a problem invoking Test
         * or Main
         * @throws InstantiationException When there is a problem invoking Test
         * or Main
         * @throws InvocationTargetException When there is a problem invoking
         * Test or Main
         * @throws java.io.IOException When there is a problem completing the
         * filter
         * @throws java.lang.NoSuchMethodException When a scenario cannot be
         * instantiated due to an invalid Class type or Intention
         */
        public void run(Scenario scenario, Request request) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, IllegalArgumentException, NoSuchMethodException, SecurityException {
            Boolean testMode = false;
            if (request.getParameters().get("testMode") != null) {
                testMode = true;
            }
            if (testMode) {
                scenario.getTest().getMethod().invoke((scenario instanceof Fail ? Blink.getFail() : scenario), new Object[]{request});
            } else {
                scenario.getMain().getMethod().invoke((scenario instanceof Fail ? Blink.getFail() : scenario), new Object[]{request});
            }
        }

        /**
         * Use http client tools to forward the request somewhere else and then
         * pass on the reply to the request
         *
         * @param bestNode best node to be used to handle the request
         * @param request request
         */
        public final synchronized void sendProxyHttpRequest(final Node bestNode, final Request request) {
            String redirectUrl = "";
            for (Url url : bestNode.getSupportedUrls()) {
                if (url.getPath().equalsIgnoreCase(request.getUrl().getPath())) {
                    redirectUrl = url.getAbsoluteUrl();
                    break;
                }
            }
            Response response;
            try {
                if (request.getMethod().equals(Http.Method.POST)) {
                    response = HttpRequests.sendPost(redirectUrl, Blink.getCluster().getTimeoutInMillis());
                } else {
                    response = HttpRequests.sendGet(redirectUrl, Blink.getCluster().getTimeoutInMillis());
                }
                Blink.getWebServer().respond(request, response);
            } catch (IOException ex) {
                response = new Response(Status.$404, "404 Not Found");
                Blink.getWebServer().respond(request, response);
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public class SocketHandler extends Thread {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(Blink.getNode().getPort());
                while (true) {
                    try (Socket socket = serverSocket.accept()) {
                        socket.setSoTimeout(Blink.getCluster().getTimeoutInMillis());
                        Process process = null;
                        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                            try {
                                process = (Process) objectInputStream.readObject();
                            } catch (IOException | ClassNotFoundException ex) {
                                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if ((process != null) && (process.getProcessType().equals(ProcessType.Ping))) {
                                try {
                                    objectOutputStream.writeObject(new Process(null, Blink.getNode(), ProcessType.Pong));
                                } catch (IOException ex) {
                                    Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if ((process != null) && (process.getProcessType().equals(ProcessType.TotalActionsRequest))) {
                                try {
                                    Node node = process.getNode();
                                    List<Action> nodeActions = (List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE nodeId = " + node.getId(), Action.class);
                                    Integer totalMissingActions = (Integer) process.getObject() - nodeActions.size();
                                    objectOutputStream.writeObject(new Process(totalMissingActions, Blink.getNode(), ProcessType.TotalActionsResponse));
                                } catch (IOException ex) {
                                    Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if ((process != null) && (process.getProcessType().equals(ProcessType.MissingActionRecordRequest))) {
                                Action action = (Action) process.getObject();
                                Boolean hasAction = (!((List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE ms = " + action.getMs() + " AND nodeId = " + action.getNodeId() + " LIMIT 1", Action.class)).isEmpty());
                                if (!hasAction) {
                                    Blink.getDatabase().statement("INSERT INTO action (ms, nodeId, forwardStatement, rollbackStatement, timeOfExecution) VALUES (" + action.getMs() + "," + action.getNodeId() + "," + action.getForwardStatement() + "," + action.getRollbackStatement() + "," + action.getTimeOfExecution() + ");");
                                }
                            }
                            if ((process != null) && (process.getProcessType().equals(ProcessType.SessionUpdateRequest))) {
                                for (Session remote : (HashSet<Session>) process.getObject()) {
                                    if (Blink.getSessions().contains(remote)) {
                                        Session local = (Session) Sets.get(Blink.getSessions(), remote);
                                        if (local.getCreated().isBefore(remote.getCreated())) {
                                            Blink.getSessions().add(remote);
                                        }
                                    } else {
                                        Blink.getSessions().add(remote);
                                    }
                                }
                            }
                        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public class PortScanner extends Thread {

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            List<Node> tempNodes = new ArrayList<>();
            while (true) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                }
                Long startTime = System.currentTimeMillis();
                for (Territory territory : Blink.getCluster().getExploredTerritories()) {
                    try {
                        tempNodes = ping(territory);
                    } catch (DuplicateNodeIdException ex) {
                        Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                        System.exit(0);
                    }
                }
                synchronized (Blink.getCluster().getFoundNodes()) {
                    Blink.getCluster().getFoundNodes().clear();
                    Blink.getCluster().getFoundNodes().addAll(tempNodes);
                    tempNodes.clear();
                }
                Long endTime = System.currentTimeMillis();
                Logger.getLogger(Cluster.class.getName()).log(Level.INFO, "Scanned all ports in: {0}", (endTime - startTime) / 1000 + " second(s) and found " + Blink.getCluster().getFoundNodes().size() + " node(s): " + Blink.getCluster().getFoundNodes().toString());
            }
        }

        public final synchronized List<Node> ping(final Territory territory) throws DuplicateNodeIdException {
            List<Node> tempNodes = new ArrayList<>();
            for (int i = territory.getPortStart(); i <= territory.getPortEnd(); i++) {
                Socket socket = null;
                ObjectOutputStream objectOutputStream = null;
                ObjectInputStream objectInputStream = null;
                try {
                    socket = new Socket(territory.getIp(), i);
                    Process process;
                    socket.setSoTimeout(Blink.getCluster().getTimeoutInMillis());
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(new Process(null, Blink.getNode(), ProcessType.Ping));
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    process = (Process) objectInputStream.readObject();
                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    if ((process != null) && (process.getProcessType().equals(ProcessType.Pong))) {
                        Node node = process.getNode();
                        if ((node != null) && (node.getId().equals(Blink.getNode().getId())) && ((!node.getAddress().equals(Blink.getNode().getAddress())) && (!Objects.equals(node.getPort(), Blink.getNode().getPort())))) {
                            throw new DuplicateNodeIdException("A node with the same id as this one was found during the territory exploration.");
                        }
                        tempNodes.add(node);
                    }
                } catch (ConnectException ex) {
                    //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
                    //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (IOException ex) {
                        //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return tempNodes;
        }
    }

    public class SessionManager extends Thread {

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            while (true) {
                synchronized (Blink.getCluster().getFoundNodes()) {
                    cleanSessions();
                    for (Node node : Blink.getCluster().getFoundNodes()) {
                        sendSessionUpdate(node);
                    }
                }

            }
        }

        /**
         * Clean up expired sessions
         */
        public final synchronized void cleanSessions() {
            Blink.getCluster().getActiveSessions().stream().forEach((session) -> {
                DateTime expiry = session.getCreated().plusMinutes(session.getMinutesOfLife());
                if (expiry.isBeforeNow()) {
                    Blink.getCluster().getActiveSessions().remove(session);
                }
            });
        }

        /**
         * New Session Update: Request to update session data
         *
         * @param node node
         */
        public final synchronized void sendSessionUpdate(final Node node) {
            Socket socket = null;
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                socket = new Socket(node.getAddress(), node.getPort());
                socket.setSoTimeout(Blink.getCluster().getTimeoutInMillis());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream.writeObject(new Process(Blink.getSessions(), Blink.getNode(), ProcessType.SessionUpdateRequest));
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            } catch (ConnectException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException ex) {
                    //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public final synchronized Session findByParameter(Map.Entry<Object, Object> parameter) {
            for (Session session : activeSessions) {
                for (Map.Entry<Object, Object> entry : session.getParameters().entrySet()) {
                    if (entry.equals(parameter)) {
                        return session;
                    }
                }
            }
            return null;
        }

    }

    //TODO Test this class
    public class DatabaseActionSynchronizer extends Thread {

        /**
         * <ol>
         *
         * <li>If any of the found nodes are missing action records in their
         * action table then send them actions until they are not missing any
         * more of them</li>
         *
         * </ol>
         */
        @Override
        public void run() {
            while (true) {
                for (Node node : Blink.getCluster().getFoundNodes()) {
                    Action lastSentAction = null;
                    while (totalActionsRequest(node) > 0) {
                        lastSentAction = missingActionRecordRequest(node, lastSentAction);
                    }
                }
            }
        }

        /**
         * <ol>
         *
         * <li>Send all found nodes the total number of actions persisted in
         * this node's action table</li>
         *
         * <li>Receive from each node how many they are missing</li>
         *
         * </ol>
         *
         * @param node node
         * @return Integer Integer
         */
        public final Integer totalActionsRequest(final Node node) {
            Integer totalActions = 0;
            try {
                totalActions = Blink.getDatabase().count("action", "nodeName = " + Blink.getNode().getId());
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            }
            Integer totalMissing = 0;
            Socket socket = null;
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                socket = new Socket(node.getAddress(), node.getPort());
                Process process;
                socket.setSoTimeout(Blink.getCluster().getTimeoutInMillis());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(new Process(totalActions, Blink.getNode(), ProcessType.TotalActionsRequest));
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                process = (Process) objectInputStream.readObject();
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                if ((process != null) && (process.getProcessType().equals(ProcessType.TotalActionsResponse))) {
                    if ((Integer) process.getObject() != null) {
                        totalMissing = (Integer) process.getObject();
                    }
                }
            } catch (ConnectException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException ex) {
                    //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return totalMissing;
        }

        /**
         * <ol>
         *
         * <li>Fetch the next action record from the database and send it to the
         * node</li>
         *
         * </ol>
         *
         * @param node node
         * @param lastSentAction
         * @return
         */
        public final Action missingActionRecordRequest(final Node node, final Action lastSentAction) {
            //1. Get latest or previous to last sent action in action table depending on whether it's the first record being sent or not
            Action nextSentAction = null;
            String sql;
            if (lastSentAction == null) {
                sql = "SELECT * FROM action ORDER BY ms DESC, nodeId ASC LIMIT 1;";
            } else {
                sql = "SELECT * FROM action WHERE ms < " + lastSentAction.getMs() + " ORDER BY ms DESC, nodeId ASC LIMIT 1;";
            }

            //2. Send the next missing action
            Socket socket = null;
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                nextSentAction = ((List<Action>) Blink.getDatabase().statement(sql, Action.class)).get(0);
                socket = new Socket(node.getAddress(), node.getPort());
                socket.setSoTimeout(Blink.getCluster().getTimeoutInMillis());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                objectOutputStream.writeObject(new Process(nextSentAction, Blink.getNode(), ProcessType.MissingActionRecordRequest));
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            } catch (ConnectException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException ex) {
                    //Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return nextSentAction;
        }
    }

    //TODO Test this method
    public class DatabaseActionExecutor extends Thread {

        /**
         * <ol>
         *
         * <li>Run execute method every 10 seconds</li>
         *
         * </ol>
         */
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                }
                execute();
            }
        }

        /**
         * <ol>
         *
         * <li>Scan for gaps</li>
         *
         * <li>If gaps are found then perform rollback and install</li>
         *
         * <li>If no gaps are found then select action records with ms dating
         * back more than 10 seconds ago</li>
         *
         * <li>Execute the forwardStatement on each of the returned action
         * records</li>
         *
         * </ol>
         */
        public void execute() {
            if (gapsExist()) {
                rollback();
                install();
            } else {
                try {
                    List<Action> nonExecutedActions = (List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE ms < " + (System.currentTimeMillis() - 15000), Action.class);
                    for (Action action : nonExecutedActions) {
                        Blink.getDatabase().statement(action.getForwardStatement());
                        Blink.getDatabase().statement("UPDATE action SET timeOfExecution = " + System.currentTimeMillis() + " WHERE ms = " + action.getMs() + " AND nodeId = " + action.getNodeId());
                    }
                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        /**
         * <ol>
         *
         * <li>If an non-executed action record with ms between past and future
         * ms executed action records is found then return true</li>
         *
         * </ol>
         *
         * @return true if gaps exist
         */
        public Boolean gapsExist() {
            try {
                List<Action> nonExecutedActions = (List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE timeOfExecution = null OR timeOfExecution = ''", Action.class);
                for (Action action : nonExecutedActions) {
                    List<Action> actionsBefore = (List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE ms < " + action.getMs(), Action.class);
                    List<Action> actionsAfter = (List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE ms > " + action.getMs(), Action.class);
                    if ((!actionsBefore.isEmpty()) && (!actionsAfter.isEmpty())) {
                        return true;
                    }
                }
            } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

        /**
         * <ol>
         *
         * <li>Execute rollback statements from newest executed action record to
         * to oldest non-executed action record where record has a
         * timeOfExecution</li>
         *
         * </ol>
         */
        public void rollback() {
            try {
                Action lastExecutedAction = ((List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE timeOfExecution NOT NULL AND timeOfExecution <> '' ORDER BY ms DESC LIMIT 1", Action.class)).get(0);
                Action oldestNonExecutedAction = ((List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE timeOfExecution NOT NULL AND timeOfExecution <> '' ORDER BY ms DESC LIMIT 1", Action.class)).get(0);
                List<Action> actionsToRollback = (List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE ms <= " + lastExecutedAction.getMs() + " AND ms >= " + oldestNonExecutedAction.getMs() + " AND timeOfExecution NOT NULL AND timeOfExecution <> '' ORDER BY ms DESC", Action.class);
                for (Action action : actionsToRollback) {
                    Blink.getDatabase().statement(action.getRollbackStatement());
                    Blink.getDatabase().statement("UPDATE action SET timeOfExecution = NULL WHERE ms = " + action.getMs() + " AND nodeId = " + action.getNodeId());
                }
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * <ol>
         *
         * <li>Run all actions from last executed action onwards</li>
         *
         * </ol>
         */
        public void install() {
            try {
                Action lastExecutedAction = ((List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE timeOfExecution NOT NULL AND timeOfExecution <> '' ORDER BY ms DESC LIMIT 1", Action.class)).get(0);
                List<Action> actionsToExecute = (List<Action>) Blink.getDatabase().statement("SELECT * FROM action WHERE ms < " + lastExecutedAction.getMs() + " ORDER BY ms ASC", Action.class);
                for (Action action : actionsToExecute) {
                    Blink.getDatabase().statement(action.getForwardStatement());
                    Blink.getDatabase().statement("UPDATE action SET timeOfExecution = " + System.currentTimeMillis() + " WHERE ms = " + action.getMs() + " AND nodeId = " + action.getNodeId());
                }
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public String toString() {
        return "Cluster{" + "timeoutInMillis=" + timeoutInMillis + ", exploredTerritories=" + exploredTerritories + ", foundNodes=" + foundNodes + ", activeSessions=" + activeSessions + ", socketHandler=" + socketHandler + ", portScanner=" + portScanner + ", sessionManager=" + sessionManager + ", databaseActionExecutor=" + databaseActionExecutor + ", httpRequestHandler=" + httpRequestHandler + '}';
    }

}
