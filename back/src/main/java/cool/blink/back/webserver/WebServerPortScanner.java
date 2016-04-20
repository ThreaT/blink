package cool.blink.back.webserver;

import cool.blink.back.core.Container;
import cool.blink.back.database.Territory;
import cool.blink.back.exception.DuplicateWebServerException;
import cool.blink.back.utilities.LogUtilities.Priority;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.logging.Logger;

public class WebServerPortScanner extends Thread {

    private final Long executeInterval;
    private final Integer abandonServerTimeout;
    private final List<Territory> territories;
    private final List<WebServerDetails> foundWebServers;
    private WebServerChain webServerChain;

    public WebServerPortScanner(final Long executeInterval, final Integer abandonServerTimeout, final Territory... territories) {
        this.executeInterval = executeInterval;
        this.abandonServerTimeout = abandonServerTimeout;
        this.territories = asList(territories);
        this.foundWebServers = new ArrayList<>();
    }

    public Long getExecuteInterval() {
        return executeInterval;
    }

    public Integer getAbandonServerTimeout() {
        return abandonServerTimeout;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public List<WebServerDetails> getFoundWebServers() {
        return foundWebServers;
    }

    public WebServerChain getWebServerChain() {
        return webServerChain;
    }

    public void setWebServerChain(WebServerChain webServerChain) {
        this.webServerChain = webServerChain;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        WebServer webServer = Container.getWebServer(this.webServerChain.getApplicationName());
        List<WebServerDetails> tempFoundWebServers = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(executeInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebServerPortScanner.class.getName()).log(Priority.HIGHEST, null, ex);
            }
            Long startTime = System.currentTimeMillis();
            for (Territory territory : this.territories) {
                try {
                    tempFoundWebServers = ping(territory, webServer);
                } catch (DuplicateWebServerException ex) {
                    Logger.getLogger(WebServerPortScanner.class.getName()).log(Priority.HIGHEST, null, ex);
                    System.exit(0);
                }
            }
            synchronized (this.foundWebServers) {
                this.foundWebServers.clear();
                this.foundWebServers.addAll(tempFoundWebServers);
                tempFoundWebServers.clear();
            }
            Long endTime = System.currentTimeMillis();
            Logger.getLogger(WebServerPortScanner.class.getName()).log(Priority.LOW, "Scanned all territories in: {0}", (endTime - startTime) / 1000 + " second(s) and found " + this.foundWebServers.size() + " webserver(s): " + this.foundWebServers.toString());
        }
    }

    public final synchronized List<WebServerDetails> ping(final Territory territory, final WebServer webServer) throws DuplicateWebServerException {
        List<WebServerDetails> tempFoundWebServers = new ArrayList<>();
        for (int i = territory.getPortStart(); i <= territory.getPortEnd(); i++) {
            Socket socket = null;
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                socket = new Socket(territory.getIp(), i);
                WebServerProcess webServerProcess;
                socket.setSoTimeout(this.abandonServerTimeout);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(new WebServerProcess(webServer.getWebServerDetails(), null, WebServerProcessType.Ping));
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                webServerProcess = (WebServerProcess) objectInputStream.readObject();
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                if ((webServerProcess != null) && (webServerProcess.getWebServerProcessType().equals(WebServerProcessType.Pong))) {
                    if (webServerProcess.getWebServerDetails().equals(webServer.getWebServerDetails())) {
                        throw new DuplicateWebServerException("A duplicate webserver was found during territory exploration.");
                    }
                    tempFoundWebServers.add(webServerProcess.getWebServerDetails());
                }
            } catch (ConnectException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Priority.HIGHEST, null, ex);
            } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
                //Logger.getLogger(Cluster.class.getName()).log(Priority.HIGHEST, null, ex);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(WebServerPortScanner.class.getName()).log(Priority.HIGHEST, null, ex);
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
                    //Logger.getLogger(Cluster.class.getName()).log(Priority.HIGHEST, null, ex);
                }
            }
        }
        return tempFoundWebServers;
    }
}
