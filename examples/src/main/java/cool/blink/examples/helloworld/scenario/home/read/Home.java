package cool.blink.examples.helloworld.scenario.home.read;

import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Scenario;
import cool.blink.examples.helloworld.Application;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Action;
import cool.blink.front.html.attribute.Content;
import cool.blink.front.html.attribute.HttpEquiv;
import cool.blink.front.html.attribute.InputType;
import cool.blink.front.html.attribute.Method;
import cool.blink.front.html.attribute.Name;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.attribute.Value;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Fieldset;
import cool.blink.front.html.element.Form;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.Input;
import cool.blink.front.html.element.Legend;
import cool.blink.front.html.element.Meta;
import cool.blink.front.html.element.Title;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.MarginTop;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.FloatValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.HttpEquivValue;
import cool.blink.front.html.property.value.InputTypeValue;
import cool.blink.front.html.property.value.MarginTopValue;
import cool.blink.front.html.property.value.MethodValue;
import cool.blink.front.html.property.value.WidthValue;
import java.util.logging.Logger;

public class Home extends Scenario {

    public static final HomeTemplate homeTemplate = new HomeTemplate();

    public Home(Url... urls) {
        super(Home.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running fit: {0}", this.toString());
        return Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running main: {0}", this.toString());
        Application.getWebServer().respond(request, homeTemplate.getResponse());
    }

    /**
     * Acceptance requirements:
     *
     * <ol>
     * <li>
     * Ensure that processing completes in under 10 ms.
     * </li>
     * </ol>
     *
     * @return Report
     */
    @Override
    public Report test(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running test: {0}", this.toString());
        Report report = new Report(1, 1, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        return report;
    }

    public static final class HomeTemplate {

        private Response response;
        private final Document document;
        private final Html html;
        private final Head head;
        private final Meta meta;
        private final Title title;
        private final Body body;
        private final Form form;
        private final Input input1;
        private final Input input2;
        private final Fieldset fieldset;
        private final Legend legend;
        private final Div div1_0;
        private final Div div1_1;
        private final Input input3;
        private final Div div1_2;
        private final Input input4;

        protected HomeTemplate() {
            Logger.getLogger(HomeTemplate.class.getName()).log(Priority.LOWEST, "Preparing HomeTemplate...");
            this.html = new Html();
            this.head = (Head) new Head();
            this.meta = (Meta) new Meta().append(new HttpEquiv(HttpEquivValue.content_type)).append(new Content("text/html; charset=UTF-8"));
            this.title = (Title) new Title().append(new Text("Hello World!"));
            this.body = (Body) new Body();
            this.form = (Form) new Form().append(new Action("/examples/HelloWorld/foo")).append(new Method(MethodValue.POST));
            this.input1 = (Input) new Input().append(new InputType(InputTypeValue.hidden)).append(new Value("foo")).append(new Name("action"));
            this.input2 = (Input) new Input().append(new InputType(InputTypeValue.hidden)).append(new Value("create")).append(new Name("method"));
            this.fieldset = (Fieldset) new Fieldset();
            this.legend = (Legend) new Legend().append(new Text("Register Foo"));
            this.div1_0 = (Div) new Div().append(new Style(new MarginTop(20, MarginTopValue.pixels), new Width(350, WidthValue.pixels), new Height(200, HeightValue.pixels)));
            this.div1_1 = (Div) new Div().append(new Style(new cool.blink.front.html.property.Float(FloatValue.left))).append(new Text("Name: "));
            this.input3 = (Input) new Input().append(new Name("name"));
            this.div1_2 = (Div) new Div().append(new Style(new cool.blink.front.html.property.Float(FloatValue.right)));
            this.input4 = (Input) new Input().append(new InputType(InputTypeValue.submit));
            this.document = new Document().append(
                    this.html.append(
                            this.head.append(
                                    this.title
                            ).append(
                                    this.meta
                            )
                    ).append(
                            this.body.append(
                                    this.form.append(
                                            this.input1
                                    ).append(
                                            this.input2
                                    ).append(
                                            this.fieldset.append(
                                                    this.legend
                                            ).append(
                                                    this.div1_0.append(
                                                            div1_1.append(
                                                                    this.input3
                                                            )
                                                    ).append(
                                                            this.div1_2.append(
                                                                    this.input4
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            response = new Response(Status.$200, this.document.print());
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

        public final Form getForm() {
            return form;
        }

        public final Input getInput1() {
            return input1;
        }

        public final Input getInput2() {
            return input2;
        }

        public final Fieldset getFieldset() {
            return fieldset;
        }

        public final Legend getLegend() {
            return legend;
        }

        public final Div getDiv1_0() {
            return div1_0;
        }

        public final Div getDiv1_1() {
            return div1_1;
        }

        public final Input getInput3() {
            return input3;
        }

        public final Div getDiv1_2() {
            return div1_2;
        }

        public final Input getInput4() {
            return input4;
        }

    }
}
