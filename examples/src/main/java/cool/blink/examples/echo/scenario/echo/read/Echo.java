package cool.blink.examples.echo.scenario.echo.read;

import com.sun.net.httpserver.HttpExchange;
import cool.blink.back.core.Blink;
import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Templates;

public class Echo extends Scenario {

    public static final EchoTemplate echoTemplate = new EchoTemplate();

    public Echo(Url... urls) {
        super(Echo.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Echo.class.getName()).log(Level.INFO, "Running...");
        return true;
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Echo.class.getName()).log(Level.INFO, "Running...");
        try {
            Blink.getWebServer().send(request, echoTemplate);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Echo.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public static final class EchoTemplate extends Response {

        private final Document document;
        private final Div div;

        protected EchoTemplate() {
            this.document = new Document();
            this.div = (Div) new Div().append(new Style(new Padding(15, PaddingValue.pixels), new Width(200, WidthValue.pixels), new Height(40, HeightValue.pixels), new BackgroundColor(236, 236, 236, 1.0)));
            this.document.append(this.div);
            super.setPayload(document.toString());
        }

        public EchoTemplate(HttpExchange httpExchange) {
            this.div = (Div) echoTemplate.getDiv().append(new Text(httpExchange.getRequestBody().toString()));
            this.document = echoTemplate.getDocument().replaceAll(echoTemplate.getDiv(), this.div);
            super.setPayload(document.toString());
        }

        public final Document getDocument() {
            return document;
        }

        public final Div getDiv() {
            return div;
        }

    }

}
