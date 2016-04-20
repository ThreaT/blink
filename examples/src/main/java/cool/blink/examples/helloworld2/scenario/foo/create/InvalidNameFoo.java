package cool.blink.examples.helloworld2.scenario.foo.create;

import cool.blink.back.core.Container;
import cool.blink.back.webserver.Report;
import cool.blink.back.webserver.Request;
import cool.blink.back.webserver.Scenario;
import cool.blink.back.webserver.Response;
import cool.blink.back.webserver.Response.Status;
import cool.blink.back.webserver.Url;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.webserver.WebServer;
import cool.blink.examples.helloworld2.Helloworld2;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Content;
import cool.blink.front.html.attribute.HttpEquiv;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.Meta;
import cool.blink.front.html.element.Title;
import cool.blink.front.html.property.value.HttpEquivValue;
import java.util.logging.Logger;

public final class InvalidNameFoo extends Scenario {

    public static final InvalidNameFooTemplate invalidNameFooTemplate = new InvalidNameFooTemplate();

    public InvalidNameFoo(final Url... urls) {
        super(InvalidNameFoo.class, urls);
    }

    @Override
    public final Boolean fit(final Request request) {
        Logger.getLogger(InvalidNameFoo.class.getName()).log(Priority.LOWEST, "Running fit: {0}", this.toString());
        Boolean urlsMatch = Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
        Boolean hasNoParameters = request.getParameters() == null;
        Boolean hasNullName = (request.getParameters().containsKey("name")) && ((request.getParameters().get("name") == null) || (request.getParameters().get("name").equals("")));
        return ((urlsMatch) && (hasNoParameters || hasNullName));
    }

    @Override
    public final void main(final Request request) {
        Logger.getLogger(InvalidNameFoo.class.getName()).log(Priority.LOWEST, "Running main: {0}", this.toString());
        WebServer webServer = Container.getWebServer(Helloworld2.helloworld2.getName());
        webServer.respond(request, invalidNameFooTemplate.getResponse());
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>Ensure that processing completes in under 10 ms</li>
     * </ol>
     *
     * @return
     */
    @Override
    public final Report test(final Request request) {
        Logger.getLogger(InvalidNameFoo.class.getName()).log(Priority.LOWEST, "Running test: {0}", this.toString());
        Report report = new Report(1, 1, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
            report.appendLog(new String[][]{{"" + Thread.currentThread().getStackTrace()[1].getLineNumber(), "Processing took too long, it took " + (end - start) + " ms."}});
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        return report;
    }

    public static final class InvalidNameFooTemplate {

        private Response response;
        private final Document document;
        private final Html html;
        private final Head head;
        private final Meta meta;
        private final Title title;
        private final Body body;

        protected InvalidNameFooTemplate() {
            this.html = new Html();
            this.head = (Head) new Head();
            this.meta = (Meta) new Meta().append(new HttpEquiv(HttpEquivValue.content_type)).append(new Content("text/html; charset=UTF-8"));
            this.title = (Title) new Title().append(new Text("Hello World!"));
            this.body = (Body) new Body().append(new Text("Invalid Foo!"));
            this.document = new Document().append(
                    this.html.append(
                            this.head.append(
                                    this.title
                            ).append(
                                    this.meta
                            )
                    ).append(
                            this.body
                    )
            );
            this.response = new Response(Status.$200, this.document.print());
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public final Document getDocument() {
            return document;
        }

        public final Html getHtml() {
            return html;
        }

        public final Head getHead() {
            return head;
        }

        public final Meta getMeta() {
            return meta;
        }

        public final Title getTitle() {
            return title;
        }

        public final Body getBody() {
            return body;
        }

    }
}
