package cool.blink.back.core;

import cool.blink.back.cloud.Dialog;
import cool.blink.back.cloud.Postrun;
import cool.blink.back.cloud.Prerun;
import cool.blink.back.cluster.Cluster;
import cool.blink.back.cluster.Node;
import cool.blink.back.database.Database;
import cool.blink.back.search.Result;
import cool.blink.back.session.Session;
import cool.blink.back.utilities.Logs;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.back.webserver.WebServer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Blink {

    private final Prerun prerun;
    private final Postrun postrun;
    private static Cluster cluster;
    private static Node node;
    private static List<Scenario> scenarios;
    private static List<Result> results;
    private static Scenario fail;
    private static Database database;
    private static WebServer webServer;

    /*
     * Constructor for basic web-apps
     */
    public Blink(final Scenario fail, final WebServer webServer, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = null;
        this.postrun = null;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.scenarios = Arrays.asList(scenarios);
        Blink.fail = fail;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    /*
     * Constructor for basic web-apps with prerun and postrun scripts
     */
    public Blink(final Prerun prerun, final Postrun postrun, final Scenario fail, final WebServer webServer, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = prerun;
        this.postrun = postrun;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.scenarios = Arrays.asList(scenarios);
        Blink.fail = fail;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    /*
     * Constructor for web-apps with a database
     */
    public Blink(final Scenario fail, final WebServer webServer, final Database database, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = null;
        this.postrun = null;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.scenarios = Arrays.asList(scenarios);
        Blink.fail = fail;
        Blink.database = database;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    /*
     * Constructor for web-apps with a database and prerun and postrun scripts
     */
    public Blink(final Prerun prerun, final Postrun postrun, final Scenario fail, final WebServer webServer, final Database database, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = prerun;
        this.postrun = postrun;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.scenarios = Arrays.asList(scenarios);
        Blink.fail = fail;
        Blink.database = database;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    /*
     * Constructor for web-apps with a search
     */
    public Blink(final List<Result> results, final Scenario fail, final WebServer webServer, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = null;
        this.postrun = null;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.results = results;
        Blink.fail = fail;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    /*
     * Constructor for web-apps with a search and prerun and postrun scripts
     */
    public Blink(final Prerun prerun, final Postrun postrun, final List<Result> results, final Scenario fail, final WebServer webServer, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = prerun;
        this.postrun = postrun;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.results = results;
        Blink.fail = fail;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    /*
     * Constructor for web-apps with a search and a database
     */
    public Blink(final List<Result> results, final Scenario fail, final WebServer webServer, final Database database, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = null;
        this.postrun = null;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.results = results;
        Blink.fail = fail;
        Blink.database = database;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    /*
     * Constructor for web-apps with a search, a database and prerun and postrun scripts
     */
    public Blink(final Prerun prerun, final Postrun postrun, final List<Result> results, final Scenario fail, final WebServer webServer, final Database database, final Cluster cluster, final Node node, final Scenario... scenarios) {
        this.prerun = prerun;
        this.postrun = postrun;
        Blink.cluster = cluster;
        Blink.node = node;
        Blink.results = results;
        Blink.fail = fail;
        Blink.database = database;
        Blink.webServer = webServer;
        Blink.scenarios = new ArrayList();
        Blink.scenarios.addAll(Arrays.asList(scenarios));
        Blink.scenarios.add(Blink.fail);
        prepareSupportedUrlsForCluster();
    }

    public final void prepareSupportedUrlsForCluster() {
        for (Scenario scenario : Blink.scenarios) {
            for (Url url : scenario.getUrls()) {
                Blink.node.getSupportedUrls().add(url);
            }
        }
    }

    public static final void enableFileLogging(final String applicationName, final Level level) {
        java.util.logging.LogManager.getLogManager().reset();
        Logs.setAllLoggersToWriteToFile(applicationName + ".xml", 209715200, level);
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

    public final Prerun getPrerun() {
        return prerun;
    }

    public final Postrun getPostrun() {
        return postrun;
    }

    public static List<Scenario> getScenarios() {
        return scenarios;
    }

    public static void setScenarios(List<Scenario> scenarios) {
        Blink.scenarios = scenarios;
    }

    public static List<Result> getResults() {
        return results;
    }

    public static void setResults(List<Result> results) {
        Blink.results = results;
    }

    /**
     * The scenario that should execute if everything else fails.
     *
     * @return Scenario
     */
    public static Scenario getFail() {
        return fail;
    }

    public static void setFail(Scenario fail) {
        Blink.fail = fail;
    }

    public static Cluster getCluster() {
        return cluster;
    }

    public static void setCluster(Cluster cluster) {
        Blink.cluster = cluster;
    }

    public static Database getDatabase() {
        return database;
    }

    public static Node getNode() {
        return node;
    }

    public static void setNode(Node node) {
        Blink.node = node;
    }

    public static void setDatabase(Database database) {
        Blink.database = database;
    }

    public static WebServer getWebServer() {
        return webServer;
    }

    public static void setWebServer(WebServer webServer) {
        Blink.webServer = webServer;
    }

    public final void start() {
        if (this.prerun != null) {
            for (Dialog dialog : this.prerun.getDialogs()) {
                dialog.execute();
            }
            if (!this.prerun.getDialogs().isEmpty()) {
                Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "{0} prerun dialogs executed.", this.prerun.getDialogs().size());
            }
        }
        Blink.webServer.start();
        Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "WebServer Started.");
        Blink.cluster.getHttpRequestHandler().start();
        Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "Http Request Handler Started.");
        Blink.cluster.getSocketHandler().start();
        Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "Socket Handler Started.");
        Blink.cluster.getPortScanner().start();
        Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "Port Scanner Started.");
        Blink.cluster.getSessionManager().start();
        Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "Session Cleaner Started.");
        if (database != null) {
            Blink.cluster.getDatabaseActionSynchronizer().start();
            Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "Database Synchronizer Started.");
            Blink.cluster.getDatabaseActionExecutor().start();
            Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "Database Action Executor Started.");
        }
        if (this.postrun != null) {
            for (Dialog dialog : this.postrun.getDialogs()) {
                dialog.execute();
            }
            if (!this.postrun.getDialogs().isEmpty()) {
                Logger.getLogger(Blink.class.getName()).log(Priority.MEDIUM, "{0} postrun dialogs executed.", this.postrun.getDialogs().size());
            }
        }
    }
}
