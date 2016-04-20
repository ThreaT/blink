package cool.blink.back.webserver;

import cool.blink.back.utilities.LongUtilities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class WebServerDetails implements Serializable {

    private final Long id;
    private final String ip;
    private final Integer port;
    private List<Url> supportedUrls;
    private Integer requestQueueSize;
    private Boolean running;

    public WebServerDetails(final String ip, final Integer port) {
        this.id = LongUtilities.generateUniqueTimeInMillis();
        this.ip = ip;
        this.port = port;
        this.supportedUrls = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public List<Url> getSupportedUrls() {
        return supportedUrls;
    }

    public void setSupportedUrls(List<Url> supportedUrls) {
        this.supportedUrls = supportedUrls;
    }

    public Integer getRequestQueueSize() {
        return requestQueueSize;
    }

    public void setRequestQueueSize(Integer requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.ip);
        hash = 29 * hash + Objects.hashCode(this.port);
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
        final WebServerDetails other = (WebServerDetails) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        return Objects.equals(this.port, other.port);
    }

    @Override
    public String toString() {
        return "WebServerDetails{" + "id=" + id + ", ip=" + ip + ", port=" + port + ", supportedUrls=" + supportedUrls + ", requestQueueSize=" + requestQueueSize + ", running=" + running + '}';
    }

}
