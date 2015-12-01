package cool.blink.back.database;

import java.util.Arrays;
import java.util.List;

public final class PreparedEntry {

    private final String sql;
    private final List<Parameter> parameters;

    public PreparedEntry(final String sql, final Parameter... parameters) {
        this.sql = sql;
        this.parameters = Arrays.asList(parameters);
    }

    public final String getSql() {
        return sql;
    }

    public final List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public final String toString() {
        return "PreparedEntry{" + "sql=" + sql + ", parameters=" + parameters + '}';
    }

}
