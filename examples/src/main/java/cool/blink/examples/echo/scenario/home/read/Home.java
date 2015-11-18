package cool.blink.examples.echo.scenario.home.read;

import cool.blink.back.core.Blink;
import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.ButtonType;
import cool.blink.front.html.attribute.Content;
import cool.blink.front.html.attribute.HttpEquiv;
import cool.blink.front.html.attribute.Id;
import cool.blink.front.html.attribute.InputType;
import cool.blink.front.html.attribute.Onclick;
import cool.blink.front.html.attribute.ScriptType;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Button;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Fieldset;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.Input;
import cool.blink.front.html.element.Legend;
import cool.blink.front.html.element.Meta;
import cool.blink.front.html.element.Script;
import cool.blink.front.html.element.Title;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.MarginTop;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.ButtonTypeValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.HttpEquivValue;
import cool.blink.front.html.property.value.InputTypeValue;
import cool.blink.front.html.property.value.MarginTopValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.front.template.javascript.InnerHtml;
import cool.blink.front.template.javascript.Value;
import cool.blink.front.template.javascript.WebSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home extends Scenario {

    public static final HomeTemplate homeTemplate = new HomeTemplate();

    public Home(Url... urls) {
        super(Home.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Running...");
        return true;
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Running...");
        Blink.getWebServer().respond(request, homeTemplate.getResponse());
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
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Starting");
        Report report = new Report(1, 1, "100%", "");
        Long start = System.currentTimeMillis();
        main(request);
        Long end = System.currentTimeMillis();
        if (end - start > 10) {
            report.setSuccessful(report.getSuccessful() - 1);
            report.appendLog(new String[][]{{"" + Thread.currentThread().getStackTrace()[1].getLineNumber(), "Processing took too long, it took " + (end - start) + " ms."}});
        }
        report.setPercentage(report.calculatePercentage(report.getTotal(), report.getSuccessful()));
        Logger.getLogger(Home.class.getName()).log(Level.INFO, "Finished");
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
        private final Div div1;
        private final Fieldset fieldset;
        private final Legend legend;
        private final Div div2;
        private final Input input;
        private final Div div3;
        private final WebSocket webSocket;
        private final Button button1;
        private final Button button2;
        private final Button button3;
        private final Div div4;
        private final Script script;

        protected HomeTemplate() {

            this.html = new Html();
            this.head = (Head) new Head();
            this.meta = (Meta) new Meta().append(new Content("text/html; charset=UTF-8")).append(new HttpEquiv(HttpEquivValue.content_type));
            this.title = (Title) new Title().append(new Text("echo"));
            this.body = (Body) new Body();
            this.div1 = (Div) new Div().append(new Id("div1")).append(new Style(new MarginTop(20, MarginTopValue.pixels), new Width(350, WidthValue.pixels), new Height(200, HeightValue.pixels)));
            this.fieldset = (Fieldset) new Fieldset();
            this.legend = (Legend) new Legend().append(new Text("Send Message"));
            this.div2 = (Div) new Div();
            this.input = (Input) new Input().append(new InputType(InputTypeValue.text)).append(new Id("messageinput"));
            this.div3 = (Div) new Div();
            this.webSocket = new WebSocket(Boolean.FALSE, new InnerHtml().add("messageoutput", "\"<br/> WebSocket is already opened.\"").getFunction(), "ws://localhost:8090/examples/echo/echo", new InnerHtml().add("messageoutput", "\"<br/>\" + event.data;").getFunction(), new InnerHtml().add("messageoutput", "\"<br/>\" + event.data;").getFunction(), new InnerHtml().add("messageoutput", "\"<br/>Connection closed\";").getFunction(), new Value("messageinput").getFunction());
            this.button1 = (Button) new Button().append(new ButtonType(ButtonTypeValue.button)).append(new Onclick(webSocket.getOpenSocketCall())).append(new Text("Open"));
            this.button2 = (Button) new Button().append(new ButtonType(ButtonTypeValue.button)).append(new Onclick(webSocket.getSendMessageCall())).append(new Text("Send"));
            this.button3 = (Button) new Button().append(new ButtonType(ButtonTypeValue.button)).append(new Onclick(webSocket.getCloseSocketCall())).append(new Text("Close"));
            this.div4 = (Div) new Div().append(new Id("messageoutput"));
            this.script = (Script) new Script().append(new ScriptType("text/javascript")).append(new Text(webSocket.getFunction()));
            this.document = new Document().append(
                    this.html.append(
                            this.head.append(
                                    this.meta
                            ).append(
                                    this.title
                            )
                    ).append(
                            this.body.append(
                                    this.div1.append(
                                            this.fieldset.append(
                                                    this.legend
                                            ).append(
                                                    this.div2.append(
                                                            this.input
                                                    )
                                            ).append(
                                                    this.div3.append(
                                                            this.button1
                                                    ).append(
                                                            this.button2
                                                    ).append(
                                                            this.button3
                                                    )
                                            ).append(
                                                    this.div4
                                            )
                                    )
                            ).append(
                                    this.script
                            )
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

        public final Div getDiv1() {
            return div1;
        }

        public final Fieldset getFieldset() {
            return fieldset;
        }

        public final Legend getLegend() {
            return legend;
        }

        public final Div getDiv2() {
            return div2;
        }

        public final Input getInput() {
            return input;
        }

        public final Div getDiv3() {
            return div3;
        }

        public final WebSocket getWebSocket() {
            return webSocket;
        }

        public final Button getButton1() {
            return button1;
        }

        public final Button getButton2() {
            return button2;
        }

        public final Button getButton3() {
            return button3;
        }

        public final Div getDiv4() {
            return div4;
        }

        public final Script getScript() {
            return script;
        }

    }

}
