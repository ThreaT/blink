package cool.blink.back.core;

import cool.blink.back.database.Database;
import cool.blink.back.exception.DuplicateApplicationException;
import cool.blink.back.exception.DuplicateDatabaseException;
import cool.blink.back.utilities.ListUtilities;
import cool.blink.back.webserver.WebServer;
import java.util.ArrayList;
import java.util.List;

public class Container {

    public static final List<Blink> Applications = new ArrayList<>();

    public static final void add(final Blink blink) throws DuplicateApplicationException, DuplicateDatabaseException {
        Boolean isUniqueApplication = isUniqueApplication(blink);
        if (!isUniqueApplication) {
            throw new DuplicateApplicationException("Could not add application to Container because it already contains an application with the same name.");
        }
        Boolean containsUniqueDatabases = containsUniqueDatabases(blink);
        if (!containsUniqueDatabases) {
            throw new DuplicateDatabaseException("Could not add all databases to Container because it already contains a database with the same name.");
        }
        Container.Applications.add(blink);
    }

    private static Boolean isUniqueApplication(final Blink blink) {
        Boolean isUniqueApplication = true;
        for (Blink temp : Container.Applications) {
            if (temp.getName().equalsIgnoreCase(blink.getName())) {
                isUniqueApplication = false;
            }
        }
        return isUniqueApplication;
    }

    private static Boolean containsUniqueDatabases(final Blink blink) {
        Boolean containsUniqueDatabases = true;
        List<String> allDatabaseNames = new ArrayList<>();
        for (Database tempDatabase : blink.getDatabases()) {
            allDatabaseNames.add(tempDatabase.getDatabaseChain().getDatabaseName());
        }
        for (String string : allDatabaseNames) {
            if (ListUtilities.totalEqualsIgnoreCase(string, allDatabaseNames) > 1) {
                containsUniqueDatabases = false;
            }
        }
        return containsUniqueDatabases;
    }

    public static final synchronized Database getDatabase(final String applicationName, final String databaseName) {
        for (Blink blink : Container.Applications) {
            if (blink.getName().equalsIgnoreCase(applicationName)) {
                for (Database database : blink.getDatabases()) {
                    if (database.getDatabaseChain().getDatabaseName().equalsIgnoreCase(databaseName)) {
                        return database;
                    }
                }
            }
        }
        return null;
    }

    public static final synchronized Database getDatabase(final Blink blink, final String databaseName) {
        for (Database database : blink.getDatabases()) {
            if (database.getDatabaseChain().getDatabaseName().equalsIgnoreCase(databaseName)) {
                return database;
            }
        }
        return null;
    }

    public static final synchronized WebServer getWebServer(final String applicationName) {
        for (Blink blink : Container.Applications) {
            if (blink.getName().equalsIgnoreCase(applicationName)) {
                return blink.getWebServer();
            }
        }
        return null;
    }
}
