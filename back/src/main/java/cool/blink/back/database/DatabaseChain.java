package cool.blink.back.database;

public final class DatabaseChain {

    private final String applicationName;
    private final String databaseName;
    private final String databaseDestination;

    public DatabaseChain(final String applicationName, final String databaseName, final String databaseDestination) {
        this.applicationName = applicationName;
        this.databaseName = databaseName;

        //Make sure destination has ending / when not blank
        String temp;
        if ((databaseDestination == null) || ("".equals(databaseDestination)) || (databaseDestination.isEmpty())) {
            temp = databaseDestination;
        } else if (databaseDestination.charAt(databaseDestination.length()) != '/') {
            temp = databaseDestination + "/";
        } else {
            temp = databaseDestination;
        }
        this.databaseDestination = temp;
    }

    public final String getApplicationName() {
        return applicationName;
    }

    public final String getDatabaseName() {
        return databaseName;
    }

    public final String getDatabaseDestination() {
        return databaseDestination;
    }

}
