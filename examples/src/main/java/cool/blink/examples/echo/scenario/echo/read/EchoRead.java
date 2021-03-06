package cool.blink.examples.echo.scenario.echo.read;

import cool.blink.back.core.Container;
import cool.blink.back.webserver.Report;
import cool.blink.back.webserver.Request;
import cool.blink.back.webserver.Response;
import cool.blink.back.webserver.Response.Status;
import cool.blink.back.webserver.Scenario;
import cool.blink.back.webserver.Url;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.webserver.WebServer;
import cool.blink.examples.echo.Echo;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.property.BackgroundColor;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.Padding;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.PaddingValue;
import cool.blink.front.html.property.value.WidthValue;
import java.util.logging.Logger;

public class EchoRead extends Scenario {

    public static final EchoTemplate echoTemplate = new EchoTemplate();

    public EchoRead(Url... urls) {
        super(EchoRead.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(EchoRead.class.getName()).log(Priority.LOWEST, "Running...");
        return true;
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(EchoRead.class.getName()).log(Priority.LOWEST, "Running...");
        WebServer webServer = Container.getWebServer(Echo.echo.getName());
        webServer.respond(request, echoTemplate.getResponse());
    }

    /**
     * Behaviour requirements:
     *
     * <ol>
     * <li>None.</li>
     * </ol>
     *
     * @return
     */
    @Override
    public Report test(Request request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static final class EchoTemplate {

        private Response response;
        private final Document document;
        private final Div div;

        protected EchoTemplate() {
            this.document = new Document();
            this.div = (Div) new Div().append(new Style(new Padding(15, PaddingValue.pixels), new Width(200, WidthValue.pixels), new Height(40, HeightValue.pixels), new BackgroundColor(236, 236, 236, 1.0)));
            this.document.append(this.div);
            this.response = new Response(Status.$200, this.document.toString());
        }

        public EchoTemplate(Request request) {
            this.div = (Div) echoTemplate.getDiv().append(new Text(Request.parametersToQueryString(request.getParameters())));
            this.document = echoTemplate.getDocument().replaceAll(echoTemplate.getDiv(), this.div);
            this.response = new Response(Status.$200, this.document.toString());
        }

        public final Document getDocument() {
            return document;
        }

        public final Div getDiv() {
            return div;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

    }

}
