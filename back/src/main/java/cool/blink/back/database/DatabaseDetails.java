package cool.blink.back.database;

import java.io.Serializable;
import java.util.Objects;

public final class DatabaseDetails implements Serializable {

    private final String id;
    private final String ip;
    private final Integer port;
    private final Long startupTime;

    public DatabaseDetails(final String id, final String ip, final Integer port, final Long startupTime) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.startupTime = startupTime;
    }

    public final String getId() {
        return id;
    }

    public final String getIp() {
        return ip;
    }

    public final Integer getPort() {
        return port;
    }

    public final Long getStartupTime() {
        return startupTime;
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
        final DatabaseDetails other = (DatabaseDetails) obj;
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
        return "DatabaseDetails{" + "id=" + id + ", ip=" + ip + ", port=" + port + '}';
    }

}
