package cool.blink.site.htmltofront.create;

import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Action;
import cool.blink.front.html.attribute.Id;
import cool.blink.front.html.attribute.Method;
import cool.blink.front.html.attribute.Name;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Button;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Form;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.StyleElement;
import cool.blink.front.html.element.Textarea;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.FloatValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.MethodValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.front.css.template.Reset;
import cool.blink.site.Application;
import cool.blink.site.home.read.Home;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class HtmlToFront extends Scenario {

    public static final HtmlToFrontTemplate htmlToFrontTemplate = new HtmlToFrontTemplate();
    public Map<String, Integer> elementQuantity = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public Integer unsupportedElements = 0;
    public List<ElementWrapper> elementWrappers = new ArrayList<>();

    public HtmlToFront(Url... urls) {
        super(HtmlToFront.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running fit: {0}", this.toString());
        return ((request.getParameters() != null) && (!request.getParameters().isEmpty()));
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running main: {0}", this.toString());
        try {
            String html = request.getParameters().get("html");
            String front = convertAllElements(html);
            Response response = new HtmlToFrontTemplate(html, front).getResponse();
            Application.getWebServer().respond(request, response);
        } catch (IOException ex) {
            Logger.getLogger(HtmlToFront.class.getName()).log(Priority.HIGHEST, null, ex);
        }
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

    public static final class HtmlToFrontTemplate {

        private Response response;
        private final Document document;
        private final Html html;
        private final Head head;
        private final Body body;
        private final Div content;
        private final Form htmlContentForm;
        private final Div htmlContentDiv;
        private final Textarea htmlContent;
        private final Form frontContentForm;
        private final Div frontContentDiv;
        private final Textarea frontContent;
        private final Button convertHtml;
        private final Button copyToClipboard;

        protected HtmlToFrontTemplate() {
            Logger.getLogger(HtmlToFrontTemplate.class.getName()).log(Priority.MEDIUM, "Preparing HtmlToFrontTemplate...");
            this.html = new Html();
            this.head = (Head) new Head().append(new StyleElement().append(new Text(new Reset().getReset())));
            this.body = (Body) new Body();
            this.content = (Div) new Div().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent)));
            this.htmlContentDiv = (Div) new Div().append(new Style(new Width(49, WidthValue.percent), new Height(88, HeightValue.percent), new cool.blink.front.html.property.Float(FloatValue.left)));
            this.htmlContentForm = (Form) new Form().append(new Method(MethodValue.POST)).append(new Action("/htmltofront"));
            this.htmlContent = (Textarea) new Textarea().append(new Name("html")).append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent)));
            this.convertHtml = (Button) new Button().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.pixels), new cool.blink.front.html.property.Float(FloatValue.left))).append(new Text("Convert to Front"));
            this.frontContentDiv = (Div) new Div().append(new Style(new Width(49, WidthValue.percent), new Height(88, HeightValue.percent), new cool.blink.front.html.property.Float(FloatValue.right)));
            this.frontContentForm = (Form) new Form().append(new Method(MethodValue.GET)).append(new Action("#"));
            this.frontContent = (Textarea) new Textarea().append(new Id("front")).append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent)));
            this.copyToClipboard = (Button) new Button().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.pixels), new cool.blink.front.html.property.Float(FloatValue.right))).append(new Text("Copy to Clipboard"));
            this.document = new Document()
                    .append(
                            this.html.append(
                                    this.head
                            ).append(
                                    this.body.append(
                                            this.content.append(
                                                    this.htmlContentDiv.append(
                                                            this.htmlContentForm.append(
                                                                    this.htmlContent
                                                            ).append(
                                                                    this.convertHtml
                                                            )
                                                    )
                                            ).append(
                                                    this.frontContentDiv.append(
                                                            this.frontContentForm.append(
                                                                    this.frontContent
                                                            ).append(
                                                                    this.copyToClipboard
                                                            )
                                                    )
                                            )
                                    )
                            )
                    );
            this.response = new Response(Status.$200, this.document.print());
        }

        public HtmlToFrontTemplate(final String html, final String front) {
            this.html = htmlToFrontTemplate.getHtml();
            this.head = htmlToFrontTemplate.getHead();
            this.body = htmlToFrontTemplate.getBody();
            this.content = htmlToFrontTemplate.getContent();
            this.htmlContentForm = htmlToFrontTemplate.getHtmlContentForm();
            this.htmlContentDiv = htmlToFrontTemplate.getHtmlContentDiv();
            this.htmlContent = (Textarea) htmlToFrontTemplate.getHtmlContent().append(new Text(html));
            this.frontContentForm = htmlToFrontTemplate.getFrontContentForm();
            this.frontContentDiv = htmlToFrontTemplate.getFrontContentDiv();
            this.frontContent = (Textarea) htmlToFrontTemplate.getFrontContent().append(new Text(front));
            this.convertHtml = htmlToFrontTemplate.getConvertHtml();
            this.copyToClipboard = htmlToFrontTemplate.getCopyToClipboard();
            //Textarea newFrontContent = (Textarea) new Textarea().append(new Id("front")).append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent))).append(new Text(front));
            this.document = htmlToFrontTemplate.getDocument().replaceAll(htmlToFrontTemplate.getHtmlContent(), this.htmlContent).replaceAll(htmlToFrontTemplate.getFrontContent(), this.frontContent);
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

        public final Body getBody() {
            return body;
        }

        public final Div getContent() {
            return content;
        }

        public final Form getHtmlContentForm() {
            return htmlContentForm;
        }

        public final Div getHtmlContentDiv() {
            return htmlContentDiv;
        }

        public final Textarea getHtmlContent() {
            return htmlContent;
        }

        public final Form getFrontContentForm() {
            return frontContentForm;
        }

        public final Div getFrontContentDiv() {
            return frontContentDiv;
        }

        public final Textarea getFrontContent() {
            return frontContent;
        }

        public final Button getConvertHtml() {
            return convertHtml;
        }

        public final Button getCopyToClipboard() {
            return copyToClipboard;
        }
    }

    public String convertAllElements(String payload) throws UnsupportedEncodingException {
        String java = "";
        String unescapedPayload = URLDecoder.decode(payload, "UTF-8");
        Source source = new Source(unescapedPayload);
        List<Element> allElements = source.getAllElements();
        for (Element element : allElements) {
            ElementWrapper elementWrapper;
            if (element.getDepth() < 2) {
                elementWrapper = new ElementWrapper(element, element.getName());
            } else {
                elementWrapper = new ElementWrapper(element, element.getName() + "_" + instanceCount(element) + "_" + element.getDepth());
            }
            elementWrappers.add(elementWrapper);
        }
        for (ElementWrapper elementWrapper : elementWrappers) {
            if (isElementSupported(elementWrapper.element)) {
                java += createSupportedInstance(elementWrapper);
            } else {
                java += createUnsupportedInstance(elementWrapper);
            }
        }
        return java;
    }

    public String[] getSupportedElementNames() {
        Reflections reflections = new Reflections("cool.blink.front.html.element", new SubTypesScanner(false));
        Set<Class<? extends cool.blink.front.html.Element>> allClasses = reflections.getSubTypesOf(cool.blink.front.html.Element.class);
        String[] supportedElementNames = new String[allClasses.size()];
        int index = 0;
        for (Class clazz : allClasses) {
            supportedElementNames[index] = clazz.getSimpleName();
            index++;
        }
        return supportedElementNames;
    }

    public Boolean isElementSupported(Element element) {
        String[] supportedElements = getSupportedElementNames();
        for (String supportedElement : supportedElements) {
            if (supportedElement.equalsIgnoreCase(element.getName())) {
                return true;
            }
        }
        return false;
    }

    public String createUnsupportedInstance(ElementWrapper elementWrapper) {
        unsupportedElements++;
        return "//Unsupported " + stripIllegalCharacters(elementWrapper.element.getName()) + unsupportedElements + " = new UnsupportedElement();\n";
    }

    public String createSupportedInstance(ElementWrapper elementWrapper) {
        String instance = stripIllegalCharacters(oneWordToProperCase(elementWrapper.element.getName())) + " " + stripIllegalCharacters(elementWrapper.instanceName) + " = " + "(" + stripIllegalCharacters(oneWordToProperCase(elementWrapper.element.getName())) + ")" + " new " + stripIllegalCharacters(oneWordToProperCase(elementWrapper.element.getName())) + "()";
        if (getId(elementWrapper.element) != null) {
            instance += ".setId(" + getId(elementWrapper.element) + ")";
        }
        if (getClass(elementWrapper.element) != null) {
            instance += ".setClazz(" + getClass(elementWrapper.element) + ")";
        }
        if (getStyle(elementWrapper.element) != null) {
            instance += ".setStyle(" + getStyle(elementWrapper.element) + ")";
        }
        if (elementWrapper.element.getParentElement() != null) {
            instance += ".setParent(" + getParent(elementWrapper) + ")";
        }
        instance += ";\n";
        return instance;
    }

    public String oneWordToProperCase(String string) {
        if (string.length() > 1) {
            return string.substring(0, 1).toUpperCase() + string.toLowerCase().substring(1, string.length());
        } else {
            return string.substring(0, 1).toUpperCase();
        }
    }

    public String stripIllegalCharacters(String string) {
        String legal = string;
        String[] illegalCharacters = new String[]{"!"};
        for (String illegalCharacter : illegalCharacters) {
            if (string.contains(illegalCharacter)) {
                legal = legal.replaceAll("!", "");
            }
        }
        return legal;
    }

    public Integer instanceCount(Element element) {
        if (elementQuantity.containsKey(element.getName())) {
            elementQuantity.put(element.getName(), elementQuantity.get(element.getName()) + 1);
        } else {
            elementQuantity.put(element.getName(), 1);
        }
        return elementQuantity.get(element.getName());
    }

    public String getParent(ElementWrapper elementWrapper) {
        if (elementWrapper.element.getDepth() == 0) {
            return null;
        } else {
            Element parent = elementWrapper.element.getParentElement();
            for (ElementWrapper elementWrapper1 : elementWrappers) {
                if (elementWrapper1.element.equals(parent)) {
                    return elementWrapper1.instanceName;
                }
            }
            return null;
        }
    }

    public String getClass(Element element) {
        String clazz = null;
        Attributes attributes = element.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getKey().equalsIgnoreCase("class")) {
                clazz = "new Clazz(\"";
                clazz += attribute.getValue();
                clazz += "\")";
                return clazz;
            }
        }
        return clazz;
    }

    public String getId(Element element) {
        String id = null;
        Attributes attributes = element.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getKey().equalsIgnoreCase("id")) {
                id = "new Id(\"";
                id += attribute.getValue();
                id += "\")";
                return id;
            }
        }
        return id;
    }

    //TODO: Specific attribute objects, i.e. "new Height(20, Unit.px)";
    public String getStyle(Element element) {
        String style = null;
        Attributes attributes = element.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getKey().equalsIgnoreCase("style")) {
                style = "new Style(\"";
                style += attribute.getValue();
                style += "\")";
                return style;
            }
        }
        return style;
    }

    public class ElementWrapper {

        public Element element;
        public String instanceName;

        public ElementWrapper(Element element, String instanceName) {
            this.element = element;
            this.instanceName = instanceName;
        }

        public ElementWrapper() {
        }

    }

}
