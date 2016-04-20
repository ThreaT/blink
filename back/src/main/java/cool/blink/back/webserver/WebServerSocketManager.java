package cool.blink.back.webserver;

import cool.blink.back.core.Container;
import cool.blink.back.utilities.LogUtilities.Priority;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class WebServerSocketManager extends Thread {

    private final Long executeInterval;
    private final Integer abandonClientTimeout;
    private WebServerChain webServerChain;

    public WebServerSocketManager(final Long executeInterval, final Integer abandonClientTimeout) {
        this.executeInterval = executeInterval;
        this.abandonClientTimeout = abandonClientTimeout;
    }

    public Long getExecuteInterval() {
        return executeInterval;
    }

    public Integer getAbandonClientTimeout() {
        return abandonClientTimeout;
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
        try {
            ServerSocket serverSocket = new ServerSocket(webServer.getWebServerDetails().getPort());
            while (true) {
                try {
                    Thread.sleep(this.executeInterval);
                } catch (InterruptedException ex) {
                    Logger.getLogger(WebServerSocketManager.class.getName()).log(Priority.HIGHEST, null, ex);
                }
                try (Socket socket = serverSocket.accept()) {
                    socket.setSoTimeout(this.abandonClientTimeout);
                    WebServerProcess webServerProcess;
                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                        webServerProcess = (WebServerProcess) objectInputStream.readObject();
                        if ((webServerProcess != null) && (webServerProcess.getWebServerProcessType().equals(WebServerProcessType.Ping))) {
                            try {
                                objectOutputStream.writeObject(new WebServerProcess(webServer.getWebServerDetails(), null, WebServerProcessType.Pong));
                            } catch (IOException ex) {
                                Logger.getLogger(WebServerSocketManager.class.getName()).log(Priority.HIGHEST, null, ex);
                            }
                        }
                        if ((webServerProcess != null) && (webServerProcess.getWebServerProcessType().equals(WebServerProcessType.SessionUpdateRequest))) {
                            for (Map.Entry<String, Session> remote : ((HashMap<String, Session>) webServerProcess.getObject()).entrySet()) {
                                if (webServer.getSessionManager().getActiveSessions().containsKey("" + remote.getValue().getId())) {
                                    Session local = webServer.getSessionManager().getActiveSessions().get("" + remote.getValue().getId());
                                    if (local.getCreated().isBefore(remote.getValue().getCreated())) {
                                        webServer.getSessionManager().getActiveSessions().put("" + remote.getValue().getId(), remote.getValue());
                                    }
                                } else {
                                    webServer.getSessionManager().getActiveSessions().put("" + remote.getValue().getId(), remote.getValue());
                                }
                            }
                        }
                    } catch (EOFException | ClassNotFoundException ex) {
                        Logger.getLogger(WebServerSocketManager.class.getName()).log(Priority.HIGHEST, null, ex);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WebServerSocketManager.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

}
