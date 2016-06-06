package cool.blink.back.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Clusterable {

    private final PreparedStatement forwardStatement;
    private final PreparedStatement rollbackStatement;
    private final ResultSet generatedKeys;

    public Clusterable(final PreparedStatement forwardStatement, final PreparedStatement rollbackStatement, final ResultSet generatedKeys) {
        this.forwardStatement = forwardStatement;
        this.generatedKeys = generatedKeys;
        this.rollbackStatement = rollbackStatement;
    }

    public PreparedStatement getForwardStatement() {
        return forwardStatement;
    }

    public PreparedStatement getRollbackStatement() {
        return rollbackStatement;
    }

    public ResultSet getGeneratedKeys() {
        return generatedKeys;
    }

}
