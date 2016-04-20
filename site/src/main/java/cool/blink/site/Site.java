package cool.blink.site;

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
import cool.blink.back.search.Result;
import cool.blink.back.utilities.LogUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.webserver.HttpRequestHandler;
import cool.blink.back.webserver.SessionManager;
import cool.blink.back.webserver.WebServer;
import cool.blink.back.webserver.WebServerChain;
import cool.blink.back.webserver.WebServerDetails;
import cool.blink.back.webserver.WebServerPortScanner;
import cool.blink.back.webserver.WebServerSocketManager;
import cool.blink.site.home.read.Home;
import cool.blink.site.search.read.Search;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Site {

    public static Blink site;
    public static List<Result> results = new ArrayList<Result>() {
        {
            add(new Result("HTML to Front Converter", "Converts raw HTML into Java for Blink front."));
            add(new Result("Scenario Creator", "Utility for quickly generating the source code for scenarios"));
        }
    };

    public static void main(String args[]) {
        //Blink.enableFileLogging("site1", Priority.HIGH);
        Blink.enableConsoleLogging(Priority.LOWEST);
        try {
            String name = "site";
            Cloud cloud = null;
            List<Database> databases = null;
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
                    new Redirect(name, new Url("http://127.0.0.1:80", false), new Url("http://127.0.0.1:80/home", false)),
                    new Redirect(name, new Url("http://blink.cool:80", false), new Url("http://blink.cool:80/home", false)),
                    new Redirect(name, new Url("http://www.blink.cool:80", false), new Url("http://www.blink.cool:80/home", false)),
                    new Search(new Url("http://127.0.0.1:80/search", true), new Url("http://blink.cool:80/search", true)),
                    new Home(new Url("http://127.0.0.1:80/home", true), new Url("http://blink.cool:80/home", true), new Url("http://www.blink.cool:80/home", true)),
                    new cool.blink.site.htmltofront.create.HtmlToFront(new Url("http://127.0.0.1:80/htmltofront", true), new Url("http://blink.cool:80/htmltofront", true), new Url("http://www.blink.cool:80/htmltofront", true)),
                    new cool.blink.site.htmltofront.read.HtmlToFront(new Url("http://127.0.0.1:80/htmltofront", true), new Url("http://blink.cool:80/htmltofront", true), new Url("http://www.blink.cool:80/htmltofront", true)),
                    new cool.blink.site.scenariocreator.create.ScenarioCreator(new Url("http://127.0.0.1:80/scenariocreator", true), new Url("http://blink.cool:80/scenariocreator", true), new Url("http://www.blink.cool:80/scenariocreator", true)),
                    new cool.blink.site.scenariocreator.read.ScenarioCreator(new Url("http://127.0.0.1:80/scenariocreator", true), new Url("http://blink.cool:80/scenariocreator", true), new Url("http://www.blink.cool:80/scenariocreator", true))
            );
            Site.site = new Blink(name, cloud, databases, webServer);
            site.start();
        } catch (IOException | InvalidPortsException | DuplicateApplicationException | DuplicateDatabaseException ex) {
            Logger.getLogger(Site.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }
}
