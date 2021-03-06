package cool.blink.examples.helloworld1;

import cool.blink.back.cloud.Cloud;
import cool.blink.back.core.Blink;
import cool.blink.back.webserver.Url;
import cool.blink.back.database.ActionExecutor;
import cool.blink.back.database.ActionSynchronizer;
import cool.blink.back.database.Database;
import cool.blink.back.database.DatabaseBackup;
import cool.blink.back.database.DatabaseChain;
import cool.blink.back.database.DatabaseDetails;
import cool.blink.back.database.DatabasePortScanner;
import cool.blink.back.database.DatabaseSocketManager;
import cool.blink.back.database.Territory;
import cool.blink.back.exception.DuplicateApplicationException;
import cool.blink.back.exception.DuplicateDatabaseException;
import cool.blink.back.exception.InvalidPortsException;
import cool.blink.back.utilities.DateUtilities;
import cool.blink.back.utilities.LogUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.webserver.Fail;
import cool.blink.back.webserver.HttpRequestHandler;
import cool.blink.back.webserver.Redirect;
import cool.blink.back.webserver.SessionManager;
import cool.blink.back.webserver.WebServerSocketManager;
import cool.blink.back.webserver.WebServer;
import cool.blink.back.webserver.WebServerChain;
import cool.blink.back.webserver.WebServerDetails;
import cool.blink.back.webserver.WebServerPortScanner;
import cool.blink.examples.helloworld1.scenario.foo.create.InvalidNameFoo;
import cool.blink.examples.helloworld1.scenario.foo.create.ValidFoo;
import cool.blink.examples.helloworld1.scenario.home.read.Home;
import java.io.File;
import java.io.IOException;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.logging.Logger;

public class Helloworld1 {

    public static Blink helloworld1;

    public static void main(String args[]) {
        //Blink.enableFileLogging("helloworld1", Priority.HIGH);
        Blink.enableConsoleLogging(Priority.LOWEST);
        try {
            String name = "helloworld1";
            Cloud cloud = null;
            List<Database> databases = asList(
                    new Database(
                            new DatabaseDetails("db1", "localhost", 1111, System.currentTimeMillis()),
                            new DatabaseChain(name, "db1", ""),
                            new DatabaseBackup(new File("database_backups1"), DateUtilities.TWO_HOURS_TO_MILLISECONDS),
                            new DatabaseSocketManager(10L, 10000),
                            new DatabasePortScanner(10000L, 10000, new Territory("localhost", 2222, 2222), new Territory("localhost", 5555, 5555)),
                            new ActionSynchronizer(10000L, 10000),
                            new ActionExecutor(15000L),
                            new InitializeDb1()
                    ),
                    new Database(
                            new DatabaseDetails("db5", "localhost", 5555, System.currentTimeMillis()),
                            new DatabaseChain(name, "db5", ""),
                            new DatabaseBackup(new File("database_backups5"), DateUtilities.TWO_HOURS_TO_MILLISECONDS),
                            new DatabaseSocketManager(10L, 10000),
                            new DatabasePortScanner(10000L, 10000, new Territory("localhost", 1111, 1111)),
                            new ActionSynchronizer(10000L, 10000),
                            new ActionExecutor(15000L),
                            new InitializeDb5()
                    )
            );
            WebServer webServer = new WebServer(
                    new WebServerChain(name),
                    new WebServerDetails("localhost", 9999),
                    8181,
                    null,
                    null,
                    null,
                    new WebServerSocketManager(600000L, 10000),
                    new WebServerPortScanner(600000L, 10000, new Territory("localhost", 8888, 8888)),
                    new HttpRequestHandler(10L, 10000),
                    new SessionManager(600000L, 10000),
                    new Fail(name),
                    new Redirect(name, new Url("http://localhost:8181/examples/HelloWorld/", false), new Url("http://localhost:8081/examples/HelloWorld/home", true)),
                    new ValidFoo(new Url("http://localhost:8181/examples/HelloWorld/foo", false)),
                    new InvalidNameFoo(new Url("http://localhost:8181/examples/HelloWorld/foo", false)),
                    new Home(new Url("http://localhost:8181/examples/HelloWorld/home", true))
            );
            Helloworld1.helloworld1 = new Blink(name, cloud, databases, webServer);
            helloworld1.start(Boolean.TRUE);
        } catch (IOException | InvalidPortsException | DuplicateApplicationException | DuplicateDatabaseException ex) {
            Logger.getLogger(Helloworld1.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }
}
