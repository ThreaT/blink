package cool.blink.back.cluster;

import cool.blink.back.core.Url;
import cool.blink.back.session.Session;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedTransferQueue;
import cool.blink.back.utilities.Sockets;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;

public final class Node implements Serializable {

    private final Long id;
    private final String address;
    private final Integer port;
    private final List<Url> supportedUrls;
    private Boolean handlingRequests;
    private final transient LinkedTransferQueue requestQueue;
    private transient Integer requestQueueSize;
    private transient Socket socket;
    private static final Map<String, Session> sessions = new HashMap<>();

    public Node(final Long id, final String address, final Integer port) throws BindException {
        this.id = id;
        this.address = address;
        if (Sockets.isPortInUse(address, port)) {
            throw new BindException("Address already in use: " + port);
        }
        this.port = port;
        this.supportedUrls = new ArrayList<>();
        this.handlingRequests = true;
        this.requestQueue = new LinkedTransferQueue();
        this.requestQueueSize = 0;
        this.socket = new Socket();
    }

    public Long getId() {
        return id;
    }

    public final String getAddress() {
        return address;
    }

    public final Integer getPort() {
        return port;
    }

    public final List<Url> getSupportedUrls() {
        return supportedUrls;
    }

    public final Boolean getHandlingRequests() {
        return handlingRequests;
    }

    public void setHandlingRequests(final Boolean handlingRequests) {
        this.handlingRequests = handlingRequests;
    }

    public final void setRequestQueueSize(final Integer requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
    }

    public final Integer getRequestQueueSize() {
        if (this.requestQueue == null) {
            this.requestQueueSize = 0;
        } else {
            this.requestQueueSize = this.requestQueue.size();
        }
        return requestQueueSize;
    }

    public final LinkedTransferQueue getRequestQueue() {
        return requestQueue;
    }

    public final Socket getSocket() {
        return socket;
    }

    public final void setSocket(final Socket socket) {
        this.socket = socket;
    }

    public static Map<String, Session> getSessions() {
        return sessions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Node{" + "id=" + id + ", address=" + address + ", port=" + port + ", supportedUrls=" + supportedUrls + ", handlingRequests=" + handlingRequests + ", requestQueue=" + requestQueue + ", requestQueueSize=" + requestQueueSize + ", socket=" + socket + ", sessions=" + Node.sessions.toString() + '}';
    }

}
