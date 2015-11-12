package cool.blink.back.core;

import cool.blink.back.cluster.Cluster;
import cool.blink.back.cluster.Node;
import cool.blink.back.database.Database;
import cool.blink.back.search.Result;
import cool.blink.back.session.Session;
import cool.blink.back.utilities.Logs;
import cool.blink.back.webserver.WebServer;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.cert.CertificateException;

public abstract class Blink {

    private static Cluster cluster;
    private static Node node;
    private static List<Scenario> scenarios;
    private static List<Result> results;
    private static Scenario fail;
    private static Database database;
    private static WebServer webServer;
    private static final Set<Session> sessions = new HashSet<>();

    public Blink(Scenario fail, WebServer webServer, Cluster cluster, Node node, Scenario... scenarios) {
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

    public Blink(Scenario fail, WebServer webServer, Database database, Cluster cluster, Node node, Scenario... scenarios) {
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

    public Blink(List<Result> results, Scenario fail, WebServer webServer, Cluster cluster, Node node, Scenario... scenarios) {
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

    public Blink(List<Result> results, Scenario fail, WebServer webServer, Database database, Cluster cluster, Node node, Scenario... scenarios) {
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

    public static final void disableAllLogging() {
        java.util.logging.LogManager.getLogManager().reset();
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

    public static Set<Session> getSessions() {
        return sessions;
    }

    public void start() {
        try {
            webServer.startHttpServer();
            Logger.getLogger(Blink.class.getName()).log(Level.INFO, "HTTP Server Started.");
            if (webServer.getHttpsPort() != -1) {
                webServer.startHttpsServer();
                Logger.getLogger(Blink.class.getName()).log(Level.INFO, "HTTPS Server Started.");
            }
            Blink.cluster.getHttpRequestHandler().start();
            Logger.getLogger(Blink.class.getName()).log(Level.INFO, "Http Request Handler Started.");
            Blink.cluster.getSocketHandler().start();
            Logger.getLogger(Blink.class.getName()).log(Level.INFO, "Socket Handler Started.");
            Blink.cluster.getPortScanner().start();
            Logger.getLogger(Blink.class.getName()).log(Level.INFO, "Port Scanner Started.");
            Blink.cluster.getSessionManager().start();
            Logger.getLogger(Blink.class.getName()).log(Level.INFO, "Session Cleaner Started.");
            if (database != null) {
                database.createPhysicalDatabase();
                database.createAllPhysicalTables();
                Blink.cluster.getDatabaseActionSynchronizer().start();
                Logger.getLogger(Blink.class.getName()).log(Level.INFO, "Database Synchronizer Started.");
                Blink.cluster.getDatabaseActionExecutor().start();
                Logger.getLogger(Blink.class.getName()).log(Level.INFO, "Database Action Executor Started.");
            }
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException | CertificateException | IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | java.security.cert.CertificateException ex) {
            Logger.getLogger(Blink.class.getName()).log(Level.SEVERE, null, ex);
            if (webServer.getHttpServer() != null) {
                webServer.getHttpServer().stop(webServer.getHttpPort());
            }
            if (webServer.getHttpsServer() != null) {
                webServer.getHttpsServer().stop(webServer.getHttpsPort());
            }
            try {
                if (database != null) {
                    database.deletePhysicalDatabase(database.getDestination() + "/" + database.getName());
                    if (database.physicalDatabaseExists(database)) {
                        System.out.println("Problem while attempting to delete database");
                    } else {
                        System.out.println("Database deleted");
                    }
                }
            } catch (IOException | SQLException ex2) {
                Logger.getLogger(Blink.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
    }
}
