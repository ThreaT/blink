package cool.blink.examples.sessions.scenario.login.read;

import cool.blink.back.core.Container;
import cool.blink.back.webserver.Report;
import cool.blink.back.webserver.Request;
import cool.blink.back.webserver.Response;
import cool.blink.back.webserver.Response.Status;
import cool.blink.back.webserver.Scenario;
import cool.blink.back.webserver.Url;
import cool.blink.back.webserver.Session;
import cool.blink.back.utilities.DateUtilities;
import cool.blink.back.utilities.HttpUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.webserver.WebServer;
import cool.blink.examples.sessions.Sessions;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Action;
import cool.blink.front.html.attribute.Content;
import cool.blink.front.html.attribute.HttpEquiv;
import cool.blink.front.html.attribute.InputType;
import cool.blink.front.html.attribute.Method;
import cool.blink.front.html.attribute.Placeholder;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.attribute.Value;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Br;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Form;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.Input;
import cool.blink.front.html.element.Meta;
import cool.blink.front.html.element.Title;
import cool.blink.front.html.property.Display;
import cool.blink.front.html.property.value.DisplayValue;
import cool.blink.front.html.property.value.HttpEquivValue;
import cool.blink.front.html.property.value.InputTypeValue;
import cool.blink.front.html.property.value.MethodValue;
import java.util.logging.Logger;

public class Login extends Scenario {

    public Login(Url... urls) {
        super(Login.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Login.class.getName()).log(Priority.LOWEST, "Running...");
        return Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Login.class.getName()).log(Priority.LOWEST, "Running...");
        WebServer webServer = Container.getWebServer(Sessions.sessions.getName());
        if (request.getMethod().equals(HttpUtilities.Method.POST)) {
            Boolean loggedin = (webServer.getSessionManager().getActiveSessions().get("test_user") != null) && (Boolean) webServer.getSessionManager().getActiveSessions().get("test_user").getParameters().get("logged_in_status") == true;
            if (!loggedin) {
                Session session = new Session(DateUtilities.generateUniqueDateTime(), 30);
                session.getParameters().put("logged_in_status", true);
                webServer.getSessionManager().getActiveSessions().put("test_user", session);
            }
        }
        webServer.respond(request, new LoginTemplate(webServer).getResponse());
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>
     * Ensure that processing completes in under 10 ms
     * </li>
     * </ol>
     *
     * @return Report
     */
    @Override
    public Report test(Request request) {
        Logger.getLogger(Login.class.getName()).log(Priority.LOWEST, "Starting");
        Report report = new Report(1, 1, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
            report.appendLog(new String[][]{{"" + Thread.currentThread().getStackTrace()[1].getLineNumber(), "Processing took too long, it took " + (end - start) + " ms."}});
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        Logger.getLogger(Login.class.getName()).log(Priority.LOWEST, "Finished");
        return report;
    }

    public static final class LoginTemplate {

        private final Response response;
        private final Document document;
        private final Html html;
        private final Head head;
        private final Meta meta;
        private final Title title;
        private final Body body;
        private final Br br;
        private final Form form;
        private final Div info;
        private final Div credentials;
        private final Input username;
        private final Input password;
        private final Input submit;

        private LoginTemplate(final WebServer webServer) {
            Boolean loggedin = (webServer.getSessionManager().getActiveSessions().get("test_user") != null) && (Boolean) webServer.getSessionManager().getActiveSessions().get("test_user").getParameters().get("logged_in_status") == true;

            this.html = new Html();
            this.head = (Head) new Head();
            this.meta = (Meta) new Meta().append(new Content("text/html; charset=UTF-8")).append(new HttpEquiv(HttpEquivValue.content_type));
            this.title = (Title) new Title().append(new Text("login"));
            this.body = (Body) new Body();
            this.br = new Br();
            if (loggedin) {
                this.form = (Form) new Form().append(new Action("/examples/sessions/logout")).append(new Method(MethodValue.POST));
            } else {
                this.form = (Form) new Form().append(new Action("/examples/sessions/login")).append(new Method(MethodValue.POST));
            }
            if (loggedin) {
                this.info = (Div) new Div().append(new Text("You are currently logged in."));
                this.credentials = (Div) new Div().append(new Text(""));
            } else {
                this.info = (Div) new Div().append(new Text("You are not currently logged in."));
                this.credentials = (Div) new Div().append(new Text("Credentials are test/test"));
            }
            if (loggedin) {
                this.username = (Input) new Input().append(new Style(new Display(DisplayValue.none)));
                this.password = (Input) new Input().append(new Style(new Display(DisplayValue.none)));
            } else {
                this.username = (Input) new Input().append(new Placeholder("Username..."));
                this.password = (Input) new Input().append(new Placeholder("Password..."));
            }
            if (loggedin) {
                this.submit = (Input) new Input().append(new Value("logout")).append(new InputType(InputTypeValue.submit));
            } else {
                this.submit = (Input) new Input().append(new Value("login")).append(new InputType(InputTypeValue.submit));
            }
            this.document = new Document().append(
                    this.html.append(
                            this.head.append(
                                    this.meta
                            ).append(
                                    this.title
                            )
                    ).append(
                            this.body.append(
                                    this.form.append(
                                            this.info
                                    ).append(
                                            this.br
                                    ).append(
                                            this.credentials
                                    ).append(
                                            this.br
                                    ).append(
                                            this.username
                                    ).append(
                                            this.br
                                    ).append(
                                            this.password
                                    ).append(
                                            this.br
                                    ).append(
                                            this.br
                                    ).append(
                                            this.submit
                                    )
                            )
                    )
            );
            this.response = new Response(Status.$200, this.document.print());
        }

        public Response getResponse() {
            return response;
        }

        public Document getDocument() {
            return document;
        }

        public Html getHtml() {
            return html;
        }

        public Head getHead() {
            return head;
        }

        public Meta getMeta() {
            return meta;
        }

        public Title getTitle() {
            return title;
        }

        public Body getBody() {
            return body;
        }

        public Br getBr() {
            return br;
        }

        public Form getForm() {
            return form;
        }

        public Div getInfo() {
            return info;
        }

        public Input getUsername() {
            return username;
        }

        public Input getPassword() {
            return password;
        }

        public Input getSubmit() {
            return submit;
        }

    }

}
