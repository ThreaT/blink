package cool.blink.examples.echo;

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
import cool.blink.examples.echo.scenario.home.read.Home;
import cool.blink.examples.echo.scenario.echo.read.Echo;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This example will not be supported until the release of jvm http2 support
 */
public class Application extends Blink {

    private static Application application;

    public Application(Scenario fail, WebServer webServer, Cluster cluster, Node node, Scenario... scenarios) {
        super(fail, webServer, cluster, node, scenarios);
    }

    public static void main(String args[]) {
        try {
            application = new Application(
                    new Fail(),
                    new WebServer(8082, 443, new File("keystore.jks"), "password"),
                    new Cluster(3000, new Territory("localhost", 1, 65535, 250)),
                    new Node(Longs.generateUniqueTimeInMillis(), "localhost", 8083),
                    new Redirect(new Url("http://localhost/examples/echo/"), new Url("http://localhost:80/examples/echo/home")),
                    new Home(new Url("http://localhost:80/examples/echo/home")),
                    new Echo(new Url("http://localhost:80/examples/echo/echo"))
            );
        } catch (IOException | ClassNotFoundException | InvalidPortsException ex) {
            Logger.getLogger(Application.class.getName()).log(Priority.HIGHEST, null, ex);
        }
        application.start();
    }

}
