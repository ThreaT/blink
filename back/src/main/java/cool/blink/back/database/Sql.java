package cool.blink.back.database;

public class Sql {

    private final String statement;
    private final String rollback;

    public Sql(String statement, String rollback) {
        this.statement = statement;
        this.rollback = rollback;
    }

    public String getStatement() {
        return statement;
    }

    public String getRollback() {
        return rollback;
    }

}
