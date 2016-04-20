package cool.blink.back.webserver;

import cool.blink.back.core.Container;
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.joda.time.DateTime;

public class SessionManager extends Thread {

    private final Long executeInterval;
    private final Integer abandonServerTimeout;
    private final Map<String, Session> activeSessions;
    private WebServerChain webServerChain;

    public SessionManager(final Long executeInterval, final Integer abandonServerTimeout) {
        this.executeInterval = executeInterval;
        this.abandonServerTimeout = abandonServerTimeout;
        this.activeSessions = new HashMap<>();
    }

    public Long getExecuteInterval() {
        return executeInterval;
    }

    public Map<String, Session> getActiveSessions() {
        return activeSessions;
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
        while (true) {
            try {
                Thread.sleep(this.executeInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(SessionManager.class.getName()).log(Priority.HIGHEST, null, ex);
            }
            synchronized (webServer.getWebServerPortScanner().getFoundWebServers()) {
                cleanSessions();
                for (WebServerDetails webServerDetails : webServer.getWebServerPortScanner().getFoundWebServers()) {
                    sendSessionUpdate(webServerDetails);
                }
            }
        }
    }

    /**
     * Clean up expired sessions
     */
    public final synchronized void cleanSessions() {
        for (Map.Entry<String, Session> session : this.activeSessions.entrySet()) {
            DateTime expiry = session.getValue().getCreated().plusMinutes(session.getValue().getMinutesOfLife());
            if (expiry.isBeforeNow()) {
                this.activeSessions.remove(session.getKey());
            }
        }
    }

    /**
     * New Session Update: Request to update session data
     *
     * @param webServerDetails webServerDetails
     */
    public final synchronized void sendSessionUpdate(final WebServerDetails webServerDetails) {
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            socket = new Socket(webServerDetails.getIp(), webServerDetails.getPort());
            socket.setSoTimeout(this.abandonServerTimeout);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(new WebServerProcess(webServerDetails, this.activeSessions, WebServerProcessType.SessionUpdateRequest));
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (ConnectException ex) {
            //Logger.getLogger(Cluster.class.getName()).log(Priority.HIGHEST, null, ex);
        } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
            //Logger.getLogger(Cluster.class.getName()).log(Priority.HIGHEST, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SessionManager.class.getName()).log(Priority.HIGHEST, null, ex);
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

    public final synchronized Session findByParameter(Map.Entry<Object, Object> parameter) {
        for (Map.Entry<String, Session> session : this.activeSessions.entrySet()) {
            for (Map.Entry<Object, Object> entry : session.getValue().getParameters().entrySet()) {
                if (entry.equals(parameter)) {
                    return session.getValue();
                }
            }
        }
        return null;
    }

}
