package cool.blink.examples.helloworld;

import cool.blink.back.cluster.Cluster;
import cool.blink.back.cluster.Node;
import cool.blink.back.cluster.Territory;
import cool.blink.back.core.Blink;
import cool.blink.back.core.Fail;
import cool.blink.back.core.Redirect;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.database.Database;
import cool.blink.back.exception.InvalidPortsException;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.back.utilities.Longs;
import cool.blink.back.webserver.WebServer;
import cool.blink.examples.helloworld.scenario.home.read.Home;
import cool.blink.examples.helloworld.scenario.foo.create.InvalidNameFoo;
import cool.blink.examples.helloworld.table.Foo;
import cool.blink.examples.helloworld.scenario.foo.create.ValidFoo;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Application extends Blink {

    private static Application application;

    public Application(Scenario fail, WebServer webServer, Database database, Cluster cluster, Node node, Scenario... scenarios) {
        super(fail, webServer, database, cluster, node, scenarios);
    }

    public static void main(String args[]) {
        try {
            application = new Application(
                    new Fail(),
                    new WebServer(8081, 443, new File("keystore.jks"), "password"),
                    new Database("helloworld_database", "", Foo.class),
                    new Cluster(3000, new Territory("localhost", 1, 65535, 250)),
                    new Node(Longs.generateUniqueId(), "localhost", 8082),
                    new Redirect(new Url("http://localhost:8081/examples/HelloWorld/", false), new Url("http://localhost:8081/examples/HelloWorld/home", true)),
                    new ValidFoo(new Url("http://localhost:8081/examples/HelloWorld/foo", false)),
                    new InvalidNameFoo(new Url("http://localhost:8081/examples/HelloWorld/foo", false)),
                    new Home(new Url("http://localhost:8081/examples/HelloWorld/home", true))
            );
            application.start();
        } catch (IOException | ClassNotFoundException | InvalidPortsException ex) {
            Logger.getLogger(Application.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

}
