package cool.blink.examples.helloworld.scenario.foo.create;

import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Scenario;
import cool.blink.examples.helloworld.Application;
import cool.blink.back.core.Response;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.Urls;
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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class InvalidNameFoo extends Scenario {

    public static final InvalidNameFooTemplate invalidNameFooTemplate = new InvalidNameFooTemplate();

    public InvalidNameFoo(final Url... urls) {
        super(InvalidNameFoo.class, urls);
    }

    @Override
    public final Boolean fit(final Request request) {
        Logger.getLogger(InvalidNameFoo.class.getName()).log(Level.INFO, "Running fit: {0}", this.toString());
        Boolean urlsMatch = Urls.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
        Boolean hasNoParameters = request.getParameters() == null;
        Boolean hasNullName = (request.getParameters().containsKey("name")) && ((request.getParameters().get("name") == null) || (request.getParameters().get("name").equals("")));
        return ((urlsMatch) && (hasNoParameters || hasNullName));
    }

    @Override
    public final void main(final Request request) {
        Logger.getLogger(InvalidNameFoo.class.getName()).log(Level.INFO, "Running main: {0}", this.toString());
        try {
            Application.getWebServer().send(request, invalidNameFooTemplate);
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(InvalidNameFoo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        Logger.getLogger(InvalidNameFoo.class.getName()).log(Level.INFO, "Running test: {0}", this.toString());
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

    public static final class InvalidNameFooTemplate extends Response {

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
            super.setCode(200);
            super.setPayload(this.document.print());
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
