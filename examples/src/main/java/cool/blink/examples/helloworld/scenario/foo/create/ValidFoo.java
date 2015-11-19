package cool.blink.examples.helloworld.scenario.foo.create;

import cool.blink.back.core.Blink;
import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Scenario;
import cool.blink.back.utilities.Longs;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.Logs.CustomLevel;
import cool.blink.examples.helloworld.table.Foo;
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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidFoo extends Scenario {

    public static final ValidFooTemplate validFooTemplate = new ValidFooTemplate();

    public ValidFoo(Url... urls) {
        super(ValidFoo.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(ValidFoo.class.getName()).log(CustomLevel.LOWEST, "Running ValidFoo: fit(HttpExchange httpExchange)");
        if ((!request.getParameters().containsKey("name")) || (!request.getParameters().containsKey("action")) || (!request.getParameters().containsKey("method"))) {
            return false;
        } else {
            Matcher validName = Pattern.compile("[A-Za-z0-9]{3,20}").matcher(request.getParameters().get("name"));
            Matcher validAction = Pattern.compile("(foo){1}").matcher(request.getParameters().get("action"));
            Matcher validMethod = Pattern.compile("(create){1}").matcher(request.getParameters().get("method"));
            return ((validName.matches()) && (validAction.matches()) && (validMethod.matches()));
        }
    }

    @Override
    public void main(Request request) {
        try {
            Logger.getLogger(ValidFoo.class.getName()).log(CustomLevel.LOWEST, "Running ValidFoo: main(HttpExchange httpExchange)");
            Foo foo = new Foo(Longs.generateUniqueId(), Foo.class.getSimpleName().toLowerCase());
            foo.setName(request.getParameters().get("name"));
            Blink.getDatabase().createPhysicalRecord(Blink.getDatabase().populateRecordDatabaseAndTableAndCells(foo));
            Blink.getWebServer().respond(request, new ValidFooTemplate(foo).getResponse());
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException ex) {
            Logger.getLogger(ValidFoo.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>Ensure that foo was saved to the database</li>
     * <li>Ensure that foo is displayed on the interface</li>
     * <li>Ensure that processing completes in under 10 ms</li>
     * </ol>
     *
     * @return Report
     */
    @Override
    public Report test(Request request) {
        Logger.getLogger(ValidFoo.class.getName()).log(CustomLevel.LOWEST, "Running ValidFoo: test(HttpExchange httpExchange)");
        Report report = new Report(2, 2, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();

        Foo foo = new Foo();
        foo.setName(request.getParameters().get("name"));

        if (!Blink.getDatabase().physicalRecordExists(foo)) {
            report.setSuccessful(report.getSuccessful() - 1);
            report.appendLog(new String[][]{{"" + Thread.currentThread().getStackTrace()[1].getLineNumber(), "Foo was not persisted to the database for some reason."}});
        }
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
            report.appendLog(new String[][]{{"" + Thread.currentThread().getStackTrace()[1].getLineNumber(), "Processing took too long, it took " + (end - start) + " ms."}});
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        return report;
    }

    public static class ValidFooTemplate {

        private Response response;
        private final Document document;
        private final Html html;
        private final Head head;
        private final Meta meta;
        private final Title title;
        private final Foo foo;
        private final Body body;

        protected ValidFooTemplate() {
            this.html = new Html();
            this.head = (Head) new Head();
            this.meta = (Meta) new Meta().append(new HttpEquiv(HttpEquivValue.content_type)).append(new Content("text/html; charset=UTF-8"));
            this.title = (Title) new Title().append(new Text("Hello World!"));
            this.foo = new Foo();
            this.body = (Body) new Body().append(new Text("Valid Foo: null"));
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

        public ValidFooTemplate(Foo foo) {
            this.html = validFooTemplate.getHtml();
            this.head = validFooTemplate.getHead();
            this.meta = validFooTemplate.getMeta();
            this.title = validFooTemplate.getTitle();
            this.foo = validFooTemplate.getFoo();
            this.body = validFooTemplate.getBody();
            this.document = validFooTemplate.getDocument().replaceAll(this.body, new Body().append(new Text("Valid Foo: " + foo.getName())));
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

        public final Foo getFoo() {
            return foo;
        }

        public final Body getBody() {
            return body;
        }

    }

}
