package cool.blink.back.core;

import cool.blink.back.cloud.Cloud;
import cool.blink.back.cloud.Dialog;
import cool.blink.back.cloud.Postrun;
import cool.blink.back.cloud.Prerun;
import cool.blink.back.database.Database;
import cool.blink.back.exception.DuplicateApplicationException;
import cool.blink.back.exception.DuplicateDatabaseException;
import cool.blink.back.utilities.LogUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.webserver.WebServer;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Blink {

    private final String name;
    private final Cloud cloud;
    private final List<Database> databases;
    private final WebServer webServer;

    public Blink(final String name, final Cloud cloud, final List<Database> databases, final WebServer webserver) {
        this.name = name;
        this.cloud = cloud;
        this.databases = databases;
        this.webServer = webserver;
    }

    public String getName() {
        return name;
    }

    public final Cloud getCloud() {
        return cloud;
    }

    public final List<Database> getDatabases() {
        return databases;
    }

    public final WebServer getWebServer() {
        return webServer;
    }

    public final void start() throws DuplicateApplicationException, DuplicateDatabaseException {
        Container.add(this);
        if (this.cloud != null) {
            for (Prerun prerun : this.cloud.getPreruns()) {
                for (Dialog dialog : prerun.getDialogs()) {
                    dialog.execute();
                }
            }
            if (!this.cloud.getPreruns().isEmpty()) {
                Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "{0} prerun dialogs executed.", this.cloud.getPreruns().size());
            }
        }
        for (Database database : this.databases) {
            database.begin();
        }
        this.webServer.begin();
        if (this.cloud != null) {
            for (Postrun postrun : this.cloud.getPostruns()) {
                for (Dialog dialog : postrun.getDialogs()) {
                    dialog.execute();
                }
            }
            if (!this.cloud.getPostruns().isEmpty()) {
                Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "{0} postrun dialogs executed.", this.cloud.getPreruns().size());
            }
        }
    }

    public static final void enableFileLogging(final String applicationName, final Level level) {
        java.util.logging.LogManager.getLogManager().reset();
        LogUtilities.setAllLoggersToWriteToFile(applicationName + ".xml", 209715200, level);
    }

    public static final void enableConsoleLogging(final Level level) {
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(level);
        }
    }

    public static final void disableAllLogging() {
        java.util.logging.LogManager.getLogManager().reset();
    }
}
