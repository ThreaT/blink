package cool.blink.site.scenariocreator.create;

import cool.blink.back.webserver.Report;
import cool.blink.back.webserver.Request;
import cool.blink.back.webserver.Response;
import cool.blink.back.webserver.Response.Status;
import cool.blink.back.webserver.Scenario;
import cool.blink.back.webserver.Url;
import cool.blink.back.utilities.LogUtilities.Priority;
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
import cool.blink.site.home.read.Home;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ScenarioCreator extends Scenario {

    private String file = "";
    private String packageName;
    private String imports;
    private String clazz;
    private String intention;
    private String scenario;
    private String fit;
    private List<ScenarioCreator.Condition> fitConditions = new ArrayList<>();
    private String main;
    private String test;
    private String template;
    public static final ScenarioCreatorTemplate scenarioCreatorTemplate = new ScenarioCreatorTemplate();

    public ScenarioCreator(Url... urls) {
        super(ScenarioCreator.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running fit: {0}", this.toString());
        return ((request.getParameters() != null) && (!request.getParameters().isEmpty()));
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(Priority.LOWEST, "Running main: {0}", this.toString());
        //try {
        populateFitConditions();
        System.out.println("fit conditions: " + fitConditions.toString());
        //this.packageName = "package com." + jTextField1.getText().toLowerCase() + ".scenario." + jTextField7.getText().toLowerCase() + "." + jTextField5.getText().toLowerCase() + "\n\n";
        this.imports = "import com.blink.Feature;\n"
                + "import com.blink.Intention;\n"
                + "import com.blink.Protocol;\n"
                + "import com.blink.Report;\n"
                + "import com.blink.Scenario;\n"
                + "import com.blink.WebServer;\n"
                + "import java.io.IOException;\n"
                + "import java.util.logging.Level;\n"
                + "import java.util.logging.Logger;\n"
                + "import java.util.regex.Pattern;\n"
                + "import javax.servlet.http.HttpServlet;\n"
                + "import javax.servlet.http.HttpServletRequest;\n"
                + "import javax.servlet.http.HttpServletResponse;\n"
                + "import javax.websocket.Session;\n\n";
        //this.clazz = "public class " + jTextField7.getText() + " extends HttpServlet implements Feature {\n\n";
        //this.intention = "private static final Intention intention = new Intention(Protocol.HTTP, " + "\"" + jTextField8.getText() + "\", " + "\"" + jTextField5.getText() + "\", " + "\"" + jTextField6.getText() + "\", " + jCheckBox3.isSelected() + ");\n";
        //this.scenario = "public static final Scenario scenario = new Scenario(" + jTextField7.getText() + ".class, " + "intention);\n\n";
        this.fit = "@Override\npublic Boolean fit(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n" + fitConditions(fitConditions) + "\n}\n\n";
        this.main = "@Override\npublic void main(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n"
                + "try {\n"
                + "response.getWriter().println(template(session, message, request, response));\n"
                + "} catch (IOException ex) {}" + "\n}\n\n";
        this.test = "  /**\n"
                + "     * Acceptance requirements:\n"
                + "     *\n"
                + "     * <ol>\n"
                + "     * <li>\n"
                + "     * None.\n"
                + "     * </li>\n"
                + "     * </ol>\n"
                + "     *\n"
                + "     * @param session Session\n"
                + "     * @param message String\n"
                + "     * @param request HttpServletRequest\n"
                + "     * @param response HttpServletResponse\n"
                + "     * @return Report\n"
                + "     */\n"
                + "    @Override\n"
                + "    public Report test(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n"
                + "        return null;\n"
                + "    }\n\n";
        this.template = "public static String template(Session session, String message, HttpServletRequest request, HttpServletResponse response) {\n"
                + "return \"\";"
                + "        \n"
                + "    }\n";
        this.file = this.packageName + this.imports + this.clazz + this.intention + this.scenario + this.fit + this.main + this.test + this.template;
        //jTextArea1.setText(this.file);
        //Application.getWebServer().send(httpExchange, request, template.getResponse());
        //} catch (IOException | InterruptedException | CloneNotSupportedException ex) {
        //Logger.getLogger(ScenarioCreator.class.getName()).log(Priority.HIGHEST, null, ex);
        //}
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

    public static final class ScenarioCreatorTemplate {

        private Response response;
        private final Document document;
        private final Html html;
        private final Head head;
        private final StyleElement reset;
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

        protected ScenarioCreatorTemplate() {
            Logger.getLogger(ScenarioCreatorTemplate.class.getName()).log(Priority.MEDIUM, "Preparing ScenarioCreatorTemplate...");
            this.html = new Html();
            this.head = (Head) new Head();
            this.reset = new StyleElement(new Text(new Reset().getReset()).getText());
            this.body = (Body) new Body();
            this.content = (Div) new Div().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent)));
            this.htmlContentDiv = (Div) new Div().append(new Style(new Width(49, WidthValue.percent), new Height(88, HeightValue.percent), new cool.blink.front.html.property.Float(FloatValue.left)));
            this.htmlContentForm = (Form) new Form().append(new Method(MethodValue.GET)).append(new Action("/scenariocreator"));
            this.htmlContent = (Textarea) new Textarea().append(new Name("html")).append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent)));
            this.convertHtml = (Button) new Button().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.pixels), new cool.blink.front.html.property.Float(FloatValue.left))).append(new Text("Convert to Front"));
            this.frontContentDiv = (Div) new Div().append(new Style(new Width(49, WidthValue.percent), new Height(88, HeightValue.percent), new cool.blink.front.html.property.Float(FloatValue.right)));
            this.frontContentForm = (Form) new Form().append(new Method(MethodValue.GET)).append(new Action("#"));
            this.frontContent = (Textarea) new Textarea().append(new Id("front")).append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent)));
            this.copyToClipboard = (Button) new Button().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.pixels), new cool.blink.front.html.property.Float(FloatValue.right))).append(new Text("Copy to Clipboard"));
            this.document = new Document()
                    .append(
                            this.html.append(
                                    this.head.append(
                                            this.reset
                                    )
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

        public ScenarioCreatorTemplate(final String front) {
            this.html = scenarioCreatorTemplate.getHtml();
            this.head = scenarioCreatorTemplate.getHead();
            this.reset = scenarioCreatorTemplate.getReset();
            this.body = scenarioCreatorTemplate.getBody();
            this.content = scenarioCreatorTemplate.getContent();
            this.htmlContentForm = scenarioCreatorTemplate.getHtmlContentForm();
            this.htmlContentDiv = scenarioCreatorTemplate.getHtmlContentDiv();
            this.htmlContent = scenarioCreatorTemplate.getHtmlContent();
            this.frontContentForm = scenarioCreatorTemplate.getFrontContentForm();
            this.frontContentDiv = scenarioCreatorTemplate.getFrontContentDiv();
            this.frontContent = scenarioCreatorTemplate.getFrontContent();
            this.convertHtml = scenarioCreatorTemplate.getConvertHtml();
            this.copyToClipboard = scenarioCreatorTemplate.getCopyToClipboard();
            Textarea newFrontContent = (Textarea) new Textarea().append(new Id("front")).append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent))).append(new Text(front));
            this.document = scenarioCreatorTemplate.getDocument().replaceAll(this.frontContent, newFrontContent);
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

        public final StyleElement getReset() {
            return reset;
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

    public class Condition {

        private String requestParameter;
        private String regularExpression;
        private Boolean nullable;

        public Condition() {
        }

        public Condition(String requestParameter, String regularExpression, Boolean nullable) {
            this.requestParameter = requestParameter;
            this.regularExpression = regularExpression;
            this.nullable = nullable;
        }

        public String getRequestParameter() {
            return requestParameter;
        }

        public void setRequestParameter(String requestParameter) {
            this.requestParameter = requestParameter;
        }

        public String getRegularExpression() {
            return regularExpression;
        }

        public void setRegularExpression(String regularExpression) {
            this.regularExpression = regularExpression;
        }

        public Boolean getNullable() {
            return nullable;
        }

        public void setNullable(Boolean nullable) {
            this.nullable = nullable;
        }

        @Override
        public String toString() {
            return "Condition{" + "requestParameter=" + requestParameter + ", regularExpression=" + regularExpression + ", nullable=" + nullable + '}';
        }
    }

    public void populateFitConditions() {
        fitConditions.clear();
        //Integer numberOfRows = jTable2.getRowCount();
        //for (int i = 0; i < numberOfRows; i++) {
        try {
            //jTable2.getValueAt(i, 0).toString();
        } catch (NullPointerException nullPointerException) {
            //break;
        }
        //String requestParameter = jTable2.getValueAt(i, 0).toString();
        //String regularExpression = jTable2.getValueAt(i, 1).toString();
        Boolean nullable = true;
        try {
            //jTable2.getValueAt(i, 2).toString();
        } catch (NullPointerException nullPointerException) {
            nullable = false;
        }
        //Condition condition = new Condition(requestParameter, regularExpression, nullable);
        //fitConditions.add(condition);
        //}
    }

    public String fitConditions(List<Condition> conditions) {
        String fitConditions2 = "";

        if (conditions.isEmpty()) {
            fitConditions2 = "return true;";
            return fitConditions2;
        }

        fitConditions2 += "return (";

        for (int i = 0; i < conditions.size(); i++) {
            String fitCondition = "";

            if (!conditions.get(i).getNullable()) {
                fitCondition += "((request.getParameter(\"" + conditions.get(i).getRequestParameter() + "\") != null) && ";
            }
            fitCondition += "(Pattern.matches(\"" + conditions.get(i).getRequestParameter() + "\", request.getParameter(\"" + conditions.get(i).getRegularExpression() + "\")))";
            if (!conditions.get(i).getNullable()) {
                fitCondition += ")";
            }

            fitConditions2 += fitCondition;
            if (i == (conditions.size() - 1)) {
                fitConditions2 += ");";
            } else {
                fitConditions2 += " &&\n";
            }
        }

        return fitConditions2;
    }

}
