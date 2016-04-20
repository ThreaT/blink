package cool.blink.back.database;

import java.io.Serializable;

public class DatabaseProcess implements Serializable {

    private DatabaseDetails databaseDetails;
    private Object object;
    private DatabaseProcessType databaseProcessType;

    public DatabaseProcess() {

    }

    public DatabaseProcess(final DatabaseDetails databaseDetails, final Object object, final DatabaseProcessType databaseProcessType) {
        this.databaseDetails = databaseDetails;
        this.object = object;
        this.databaseProcessType = databaseProcessType;
    }

    public DatabaseDetails getDatabaseDetails() {
        return databaseDetails;
    }

    public void setDatabaseDetails(DatabaseDetails databaseDetails) {
        this.databaseDetails = databaseDetails;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public DatabaseProcessType getDatabaseProcessType() {
        return databaseProcessType;
    }

    public void setDatabaseProcessType(DatabaseProcessType databaseProcessType) {
        this.databaseProcessType = databaseProcessType;
    }

    @Override
    public String toString() {
        return "Process{" + "databaseDetails=" + databaseDetails + ", object=" + object + ", databaseProcessType=" + databaseProcessType + '}';
    }

}
