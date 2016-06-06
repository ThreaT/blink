package cool.blink.examples.echo;

import cool.blink.back.cloud.Cloud;
import cool.blink.back.core.Blink;
import cool.blink.back.database.Database;
import cool.blink.back.database.Territory;
import cool.blink.back.exception.DuplicateApplicationException;
import cool.blink.back.exception.DuplicateDatabaseException;
import cool.blink.back.webserver.Fail;
import cool.blink.back.webserver.Redirect;
import cool.blink.back.webserver.Url;
import cool.blink.back.exception.InvalidPortsException;
import cool.blink.back.utilities.LogUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.webserver.HttpRequestHandler;
import cool.blink.back.webserver.SessionManager;
import cool.blink.back.webserver.WebServer;
import cool.blink.back.webserver.WebServerChain;
import cool.blink.back.webserver.WebServerDetails;
import cool.blink.back.webserver.WebServerPortScanner;
import cool.blink.back.webserver.WebServerSocketManager;
import cool.blink.examples.echo.scenario.echo.read.EchoRead;
import cool.blink.examples.echo.scenario.home.read.Home;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * This example will not be supported until the release of jvm http2 support
 */
public class Echo {

    public static Blink echo;

    public static void main(String args[]) {
        //Blink.enableFileLogging("echo", Priority.HIGH);
        Blink.enableConsoleLogging(Priority.LOWEST);
        try {
            String name = "echo";
            Cloud cloud = null;
            List<Database> databases = null;
            WebServer webServer = new WebServer(
                    new WebServerChain(name),
                    new WebServerDetails("localhost", 9999),
                    8181,
                    8282,
                    new File("keystore.jks"),
                    "password",
                    new WebServerSocketManager(600000L, 10000),
                    new WebServerPortScanner(600000L, 10000, new Territory("localhost", 8888, 8888)),
                    new HttpRequestHandler(10L, 10000),
                    new SessionManager(600000L, 10000),
                    new Fail(name),
                    new Redirect(name, new Url("http://localhost/examples/echo/"), new Url("http://localhost:80/examples/echo/home")),
                    new Home(new Url("http://localhost:80/examples/echo/home")),
                    new EchoRead(new Url("http://localhost:80/examples/echo/echo"))
            );
            Echo.echo = new Blink(name, cloud, databases, webServer);
            echo.start(Boolean.TRUE);
        } catch (IOException | InvalidPortsException | DuplicateApplicationException | DuplicateDatabaseException ex) {
            Logger.getLogger(Echo.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }
}
