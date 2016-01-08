package cool.blink.examples.sessions;

import cool.blink.back.cluster.Cluster;
import cool.blink.back.cluster.Node;
import cool.blink.back.cluster.Territory;
import cool.blink.back.core.Blink;
import cool.blink.back.core.Fail;
import cool.blink.back.core.Redirect;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.exception.InvalidPortsException;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.back.utilities.Longs;
import cool.blink.back.webserver.WebServer;
import cool.blink.examples.sessions.scenario.login.read.Login;
import cool.blink.examples.sessions.scenario.logout.read.Logout;
import java.io.IOException;
import java.util.logging.Logger;

public class Application extends Blink {

    private static Application application;

    public Application(Scenario fail, WebServer webServer, Cluster cluster, Node node, Scenario... scenarios) {
        super(fail, webServer, cluster, node, scenarios);
    }

    public static void main(String args[]) {
        try {
            application = new Application(
                    new Fail(),
                    new WebServer(80),
                    new Cluster(3000, new Territory("localhost", 1, 65535, 250)),
                    new Node(Longs.generateUniqueTimeInMillis(), "localhost", 8083),
                    new Redirect(new Url("http://localhost:80/"), new Url("http://localhost:80/examples/sessions/login")),
                    new Redirect(new Url("http://localhost:80/examples/"), new Url("http://localhost:80/examples/sessions/login")),
                    new Redirect(new Url("http://localhost:80/examples/sessions/"), new Url("http://localhost:80/examples/sessions/login")),
                    new Login(new Url("http://localhost:80/examples/sessions/login")),
                    new Logout(new Url("http://localhost:80/examples/sessions/logout"))
            );
        } catch (IOException | ClassNotFoundException | InvalidPortsException ex) {
            Logger.getLogger(Application.class.getName()).log(Priority.HIGHEST, null, ex);
        }
        application.start();
    }

}
