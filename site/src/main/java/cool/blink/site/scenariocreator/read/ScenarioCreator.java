package cool.blink.site.scenariocreator.read;

import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.Logs.CustomLevel;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Placeholder;
import cool.blink.front.html.attribute.ScriptType;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Button;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.Input;
import cool.blink.front.html.element.StyleElement;
import cool.blink.front.html.element.Textarea;
import cool.blink.front.html.element.Title;
import cool.blink.front.html.property.Border;
import cool.blink.front.html.property.Float;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.MarginLeft;
import cool.blink.front.html.property.MarginRight;
import cool.blink.front.html.property.MarginTop;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.BorderStyleValue;
import cool.blink.front.html.property.value.BorderWidthValue;
import cool.blink.front.html.property.value.ColorNameValue;
import cool.blink.front.html.property.value.FloatValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.MarginLeftValue;
import cool.blink.front.html.property.value.MarginRightValue;
import cool.blink.front.html.property.value.MarginTopValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.front.template.html.Reset;
import cool.blink.site.Application;
import cool.blink.site.home.read.Home;
import java.util.logging.Logger;

public class ScenarioCreator extends Scenario {

    public static final ScenarioCreatorTemplate scenarioCreatorTemplate = new ScenarioCreatorTemplate();

    public ScenarioCreator(Url... urls) {
        super(ScenarioCreator.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(CustomLevel.LOWEST, "Running fit: {0}", this.toString());
        return Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(CustomLevel.LOWEST, "Running main: {0}", this.toString());
        Application.getWebServer().respond(request, scenarioCreatorTemplate.getResponse());
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
        Logger.getLogger(Home.class.getName()).log(CustomLevel.LOWEST, "Running test: {0}", this.toString());
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
        private final Title title;
        private final StyleElement reset;
        private final Body body;
        private final Div scenarioDetailsBox;
        private final Div scenarioDetailsRow;
        private final Input scenarioPackage;
        private final Input scenarioName;
        private final Div fitDetailsBox;
        private final Div fitDetailsRow;
        private final Input input1ParamName;
        private final Input input1Regex;
        private final Input input2ParamName;
        private final Input input2Regex;
        private final Input input3ParamName;
        private final Input input3Regex;
        private final Input input4ParamName;
        private final Input input4Regex;
        private final Input input5ParamName;
        private final Input input5Regex;
        private final Input input6ParamName;
        private final Input input6Regex;
        private final Input input7ParamName;
        private final Input input7Regex;
        private final Input input8ParamName;
        private final Input input8Regex;
        private final Input input9ParamName;
        private final Input input9Regex;
        private final Input input10ParamName;
        private final Input input10Regex;
        private final Div testDetailsBox;
        private final Div testDetailsRow;
        private final Input testMinimumPerformanceMs;
        private final Div templateArea;
        private final Textarea frontTemplate;
        private final Button generate;

        protected ScenarioCreatorTemplate() {
            Logger.getLogger(ScenarioCreatorTemplate.class.getName()).log(CustomLevel.MEDIUM, "Preparing ScenarioCreatorTemplate...");
            this.html = new Html();
            this.head = new Head();
            this.title = (Title) new Title().append(new Text("blink Scenario Creator"));
            this.reset = (StyleElement) new StyleElement().append(new Text(new Reset().getReset())).append(new ScriptType("text/javascript"));
            this.body = new Body();
            this.scenarioDetailsBox = (Div) new Div().append(new Style(new Width(40, WidthValue.percent), new Height(25, HeightValue.percent), new Border(1, BorderWidthValue.pixels, ColorNameValue.Black, BorderStyleValue.solid), new Float(FloatValue.left)));
            this.scenarioDetailsRow = (Div) new Div().append(new Style(new Width(100, WidthValue.percent), new Height(50, HeightValue.percent)));
            this.scenarioPackage = (Input) new Input().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent))).append(new Placeholder("Package name..."));
            this.scenarioName = (Input) new Input().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent))).append(new Placeholder("Scenario name..."));
            this.fitDetailsBox = (Div) new Div().append(new Style(new Width(40, WidthValue.percent), new Height(65, HeightValue.percent), new Float(FloatValue.left), new Border(1, BorderWidthValue.pixels, ColorNameValue.Black, BorderStyleValue.solid), new MarginTop(1, MarginTopValue.percent)));
            this.fitDetailsRow = (Div) new Div().append(new Style(new Width(100, WidthValue.percent), new Height(10, HeightValue.percent)));
            this.input1ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 1 name..."));
            this.input1Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 1 regular expression..."));
            this.input2ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 2 name..."));
            this.input2Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 2 regular expression..."));
            this.input3ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 3 name..."));
            this.input3Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 3 regular expression..."));
            this.input4ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 4 name..."));
            this.input4Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 4 regular expression..."));
            this.input5ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 5 name..."));
            this.input5Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 5 regular expression..."));
            this.input6ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 6 name..."));
            this.input6Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 6 regular expression..."));
            this.input7ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 7 name..."));
            this.input7Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 7 regular expression..."));
            this.input8ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 8 name..."));
            this.input8Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 8 regular expression..."));
            this.input9ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 9 name..."));
            this.input9Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 9 regular expression..."));
            this.input10ParamName = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.left))).append(new Placeholder("Parameter 10 name..."));
            this.input10Regex = (Input) new Input().append(new Style(new Width(49, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right))).append(new Placeholder("Parameter 10 regular expression..."));
            this.testDetailsBox = (Div) new Div().append(new Style(new Width(40, WidthValue.percent), new Height(5, HeightValue.percent), new Float(FloatValue.left), new Border(1, BorderWidthValue.pixels, ColorNameValue.Black, BorderStyleValue.solid), new MarginTop(1, MarginTopValue.percent)));
            this.testDetailsRow = (Div) new Div().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent)));
            this.testMinimumPerformanceMs = (Input) new Input().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent))).append(new Placeholder("Minimum performance in milliseconds"));
            this.templateArea = (Div) new Div().append(new Style(new Width(58, WidthValue.percent), new Height(100, HeightValue.percent), new Float(FloatValue.right), new Border(1, BorderWidthValue.pixels, ColorNameValue.Black, BorderStyleValue.solid)));
            this.frontTemplate = (Textarea) new Textarea().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent))).append(new Placeholder("Front Template..."));
            this.generate = (Button) new Button().append(new Style(new Height(10, HeightValue.percent), new Width(100, WidthValue.percent), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto), new MarginTop(1, MarginTopValue.percent))).append(new Text("Generate Scenario"));
            this.document = new Document().append(
                    this.html.append(
                            this.head.append(
                                    this.title
                            ).append(
                                    this.reset
                            )
                    ).append(
                            this.body.append(
                                    this.scenarioDetailsBox.append(
                                            this.scenarioDetailsRow.append(
                                                    this.scenarioPackage
                                            )
                                    ).append(
                                            this.scenarioDetailsRow.append(
                                                    this.scenarioName
                                            )
                                    )
                            ).append(
                                    this.templateArea.append(
                                            this.frontTemplate
                                    )
                            ).append(
                                    this.fitDetailsBox.append(
                                            this.fitDetailsRow.append(
                                                    this.input1ParamName
                                            ).append(
                                                    this.input1Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input2ParamName
                                            ).append(
                                                    this.input2Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input3ParamName
                                            ).append(
                                                    this.input3Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input4ParamName
                                            ).append(
                                                    this.input4Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input5ParamName
                                            ).append(
                                                    this.input5Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input6ParamName
                                            ).append(
                                                    this.input6Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input7ParamName
                                            ).append(
                                                    this.input7Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input8ParamName
                                            ).append(
                                                    this.input8Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input9ParamName
                                            ).append(
                                                    this.input9Regex
                                            )
                                    ).append(
                                            this.fitDetailsRow.append(
                                                    this.input10ParamName
                                            ).append(
                                                    this.input10Regex
                                            )
                                    )
                            ).append(
                                    this.testDetailsBox.append(
                                            this.testDetailsRow.append(
                                                    this.testMinimumPerformanceMs
                                            )
                                    )
                            ).append(
                                    this.generate
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

        public Document getDocument() {
            return document;
        }

        public Html getHtml() {
            return html;
        }

        public Head getHead() {
            return head;
        }

        public Title getTitle() {
            return title;
        }

        public StyleElement getReset() {
            return reset;
        }

        public Body getBody() {
            return body;
        }

        public Div getScenarioDetailsBox() {
            return scenarioDetailsBox;
        }

        public Div getScenarioDetailsRow() {
            return scenarioDetailsRow;
        }

        public Input getScenarioPackage() {
            return scenarioPackage;
        }

        public Input getScenarioName() {
            return scenarioName;
        }

        public Div getFitDetailsBox() {
            return fitDetailsBox;
        }

        public Div getFitDetailsRow() {
            return fitDetailsRow;
        }

        public Input getInput1ParamName() {
            return input1ParamName;
        }

        public Input getInput1Regex() {
            return input1Regex;
        }

        public Input getInput2ParamName() {
            return input2ParamName;
        }

        public Input getInput2Regex() {
            return input2Regex;
        }

        public Input getInput3ParamName() {
            return input3ParamName;
        }

        public Input getInput3Regex() {
            return input3Regex;
        }

        public Input getInput4ParamName() {
            return input4ParamName;
        }

        public Input getInput4Regex() {
            return input4Regex;
        }

        public Input getInput5ParamName() {
            return input5ParamName;
        }

        public Input getInput5Regex() {
            return input5Regex;
        }

        public Input getInput6ParamName() {
            return input6ParamName;
        }

        public Input getInput6Regex() {
            return input6Regex;
        }

        public Input getInput7ParamName() {
            return input7ParamName;
        }

        public Input getInput7Regex() {
            return input7Regex;
        }

        public Input getInput8ParamName() {
            return input8ParamName;
        }

        public Input getInput8Regex() {
            return input8Regex;
        }

        public Input getInput9ParamName() {
            return input9ParamName;
        }

        public Input getInput9Regex() {
            return input9Regex;
        }

        public Input getInput10ParamName() {
            return input10ParamName;
        }

        public Input getInput10Regex() {
            return input10Regex;
        }

        public Div getTestDetailsBox() {
            return testDetailsBox;
        }

        public Div getTestDetailsRow() {
            return testDetailsRow;
        }

        public Input getTestMinimumPerformanceMs() {
            return testMinimumPerformanceMs;
        }

        public Div getTemplateArea() {
            return templateArea;
        }

        public Textarea getFrontTemplate() {
            return frontTemplate;
        }

    }

}
