package cool.blink.back.session;

import cool.blink.back.utilities.Longs;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.joda.time.DateTime;

public class Session implements Serializable {

    private Long id;
    private DateTime created;
    private Integer minutesOfLife;
    private Map<Object, Object> parameters;

    public Session(DateTime created, Integer minutesOfLife) {
        this.id = Longs.generateUniqueId();
        this.created = created;
        this.minutesOfLife = minutesOfLife;
        this.parameters = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public Integer getMinutesOfLife() {
        return minutesOfLife;
    }

    public void setMinutesOfLife(Integer minutesOfLife) {
        this.minutesOfLife = minutesOfLife;
    }

    public Map<Object, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Object, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Session other = (Session) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Session{" + "id=" + id + ", created=" + created + ", minutesOfLife=" + minutesOfLife + ", parameters=" + parameters + '}';
    }

}
