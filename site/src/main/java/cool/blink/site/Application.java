package cool.blink.site;

import cool.blink.back.cluster.Cluster;
import cool.blink.back.cluster.Node;
import cool.blink.back.cluster.Territory;
import cool.blink.back.core.Blink;
import cool.blink.back.core.Fail;
import cool.blink.back.core.Redirect;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.exception.InvalidPortsException;
import cool.blink.back.search.Result;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.back.utilities.Longs;
import cool.blink.back.webserver.WebServer;
import cool.blink.site.home.read.Home;
import cool.blink.site.search.read.Search;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application extends Blink {

    private static Application application;

    public Application(List<Result> results, Scenario fail, WebServer webServer, Cluster cluster, Node node, Scenario... scenarios) {
        super(results, fail, webServer, cluster, node, scenarios);
    }

    public static void main(String args[]) {
        try {
            //Blink.enableFileLogging("blink site 1", Priority.HIGH);
            Blink.enableConsoleLogging(Priority.LOW);
            application = new Application(
                    new ArrayList<Result>() {
                        {
                            add(new Result("HTML to Front Converter", "Converts raw HTML into Java for Blink front."));
                            add(new Result("Scenario Creator", "Utility for quickly generating the source code for scenarios"));
                        }
                    },
                    new Fail(new Url("http://127.0.0.1:80/fail", true), new Url("http://blink.cool:80/fail", true), new Url("http://www.blink.cool:80/fail", true)),
                    new WebServer(80),
                    new Cluster(30000, 3000, new Territory("127.0.0.1", 1, 65535, 250)),
                    new Node(Longs.generateUniqueTimeInMillis(), "127.0.0.1", 81),
                    new Redirect(new Url("http://127.0.0.1:80", false), new Url("http://127.0.0.1:80/home", false)),
                    new Redirect(new Url("http://blink.cool:80", false), new Url("http://blink.cool:80/home", false)),
                    new Redirect(new Url("http://www.blink.cool:80", false), new Url("http://www.blink.cool:80/home", false)),
                    new Search(new Url("http://127.0.0.1:80/search", true), new Url("http://blink.cool:80/search", true)),
                    new Home(new Url("http://127.0.0.1:80/home", true), new Url("http://blink.cool:80/home", true), new Url("http://www.blink.cool:80/home", true)),
                    new cool.blink.site.htmltofront.create.HtmlToFront(new Url("http://127.0.0.1:80/htmltofront", true), new Url("http://blink.cool:80/htmltofront", true), new Url("http://www.blink.cool:80/htmltofront", true)),
                    new cool.blink.site.htmltofront.read.HtmlToFront(new Url("http://127.0.0.1:80/htmltofront", true), new Url("http://blink.cool:80/htmltofront", true), new Url("http://www.blink.cool:80/htmltofront", true)),
                    new cool.blink.site.scenariocreator.create.ScenarioCreator(new Url("http://127.0.0.1:80/scenariocreator", true), new Url("http://blink.cool:80/scenariocreator", true), new Url("http://www.blink.cool:80/scenariocreator", true)),
                    new cool.blink.site.scenariocreator.read.ScenarioCreator(new Url("http://127.0.0.1:80/scenariocreator", true), new Url("http://blink.cool:80/scenariocreator", true), new Url("http://www.blink.cool:80/scenariocreator", true))
            );
            application.start();
        } catch (IOException | ClassNotFoundException | InvalidPortsException ex) {
            Logger.getLogger(Application.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }
}
