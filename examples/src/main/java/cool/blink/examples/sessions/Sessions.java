package cool.blink.examples.sessions;

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
import cool.blink.examples.echo.Echo;
import cool.blink.examples.sessions.scenario.login.read.Login;
import cool.blink.examples.sessions.scenario.logout.read.Logout;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Sessions {

    public static Blink sessions;

    public static void main(String args[]) {
        //Blink.enableFileLogging("echo", Priority.HIGH);
        Blink.enableConsoleLogging(Priority.LOWEST);
        try {
            String name = "sessions";
            Cloud cloud = null;
            List<Database> databases = null;
            WebServer webServer = new WebServer(
                    new WebServerChain(name),
                    new WebServerDetails("localhost", 9999),
                    80,
                    null,
                    null,
                    null,
                    new WebServerSocketManager(600000L, 10000),
                    new WebServerPortScanner(600000L, 10000, new Territory("localhost", 8888, 8888)),
                    new HttpRequestHandler(10L, 10000),
                    new SessionManager(600000L, 10000),
                    new Fail(name),
                    new Redirect(name, new Url("http://localhost:80/"), new Url("http://localhost:80/examples/sessions/login")),
                    new Redirect(name, new Url("http://localhost:80/examples/"), new Url("http://localhost:80/examples/sessions/login")),
                    new Redirect(name, new Url("http://localhost:80/examples/sessions/"), new Url("http://localhost:80/examples/sessions/login")),
                    new Login(new Url("http://localhost:80/examples/sessions/login")),
                    new Logout(new Url("http://localhost:80/examples/sessions/logout"))
            );
            Sessions.sessions = new Blink(name, cloud, databases, webServer);
            sessions.start(Boolean.TRUE);
        } catch (IOException | InvalidPortsException | DuplicateApplicationException | DuplicateDatabaseException ex) {
            Logger.getLogger(Echo.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }
}
