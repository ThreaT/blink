package cool.blink.back.search;

import cool.blink.back.database.Record;
import java.util.Objects;

public class Query extends Record {

    private Long id;
    private String query;

    public Query(Long id, String query) {
        this.id = id;
        this.query = query;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "Query{" + "id=" + id + ", query=" + query + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.query);
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
        final Query other = (Query) obj;
        return Objects.equals(this.query, other.query);
    }

}
