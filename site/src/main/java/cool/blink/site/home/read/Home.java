package cool.blink.site.home.read;

import cool.blink.back.core.Report;
import cool.blink.back.core.Request;
import cool.blink.back.core.Scenario;
import cool.blink.back.core.Response;
import cool.blink.back.core.Response.Status;
import cool.blink.back.core.Url;
import cool.blink.back.utilities.HttpRequests;
import cool.blink.back.utilities.Logs.CustomLevel;
import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Id;
import cool.blink.front.html.attribute.Onclick;
import cool.blink.front.html.attribute.Oninput;
import cool.blink.front.html.attribute.ScriptType;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.Input;
import cool.blink.front.html.element.Script;
import cool.blink.front.html.element.StyleElement;
import cool.blink.front.html.element.Title;
import cool.blink.front.html.property.BackgroundColor;
import cool.blink.front.html.property.BackgroundImage;
import cool.blink.front.html.property.BackgroundRepeat;
import cool.blink.front.html.property.BackgroundSize;
import cool.blink.front.html.property.Border;
import cool.blink.front.html.property.Color;
import cool.blink.front.html.property.Cursor;
import cool.blink.front.html.property.Display;
import cool.blink.front.html.property.Float;
import cool.blink.front.html.property.FontWeight;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.MarginBottom;
import cool.blink.front.html.property.MarginLeft;
import cool.blink.front.html.property.MarginRight;
import cool.blink.front.html.property.MarginTop;
import cool.blink.front.html.property.MaxWidth;
import cool.blink.front.html.property.MinHeight;
import cool.blink.front.html.property.Padding;
import cool.blink.front.html.property.PaddingLeft;
import cool.blink.front.html.property.Position;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.Zindex;
import cool.blink.front.html.property.value.BackgroundColorValue;
import cool.blink.front.html.property.value.BackgroundRepeatValue;
import cool.blink.front.html.property.value.BackgroundSizeValue;
import cool.blink.front.html.property.value.BorderWidthValue;
import cool.blink.front.html.property.value.ColorNameValue;
import cool.blink.front.html.property.value.CursorValue;
import cool.blink.front.html.property.value.DisplayValue;
import cool.blink.front.html.property.value.FloatValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.MarginBottomValue;
import cool.blink.front.html.property.value.MarginLeftValue;
import cool.blink.front.html.property.value.MarginRightValue;
import cool.blink.front.html.property.value.MarginTopValue;
import cool.blink.front.html.property.value.MaxWidthValue;
import cool.blink.front.html.property.value.MethodValue;
import cool.blink.front.html.property.value.MinHeightValue;
import cool.blink.front.html.property.value.PaddingLeftValue;
import cool.blink.front.html.property.value.PaddingValue;
import cool.blink.front.html.property.value.PositionValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.front.template.css.Base64Backgrounds;
import cool.blink.front.template.html.NoHeightReset;
import cool.blink.front.template.javascript.Ajax;
import cool.blink.front.template.javascript.SearchBoxToggle;
import cool.blink.front.template.value.ImageValue;
import cool.blink.front.template.value.ToggleValue;
import cool.blink.site.Application;
import java.util.AbstractMap;
import java.util.logging.Logger;

public class Home extends Scenario {

    public static final HomeTemplate homeTemplate = new HomeTemplate();

    public Home(Url... urls) {
        super(Home.class, urls);
    }

    @Override
    public Boolean fit(Request request) {
        Logger.getLogger(Home.class.getName()).log(CustomLevel.LOWEST, "Running fit: {0}", this.toString());
        return Url.hasMatchingAbsoluteUrls(request.getUrl(), this.getUrls());
    }

    @Override
    public void main(Request request) {
        Logger.getLogger(Home.class.getName()).log(CustomLevel.LOWEST, "Running main: {0}", this.toString());
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

    public static final class HomeTemplate {

        private Response response;
        private final Document document;
        private final Html html;
        private final Head head;
        private final Title title;
        private final StyleElement reset;
        private final StyleElement pageStyle;
        private final StyleElement minWidthMediaQuery;
        private final StyleElement maxWidthMediaQuery;
        private final Script script;
        private final Ajax ajax;
        private final Input searchBar;
        private final Body body;
        private final Div responsiveHeader;
        private final Div resultsBox;
        private final SearchBoxToggle searchBoxToggle;
        private final Oninput oninput;
        private final Div searchDiv;
        private final Div header;
        private final Div main;
        private final Div about;
        private final Div apps;
        private final Div app1;
        private final Div app2;

        protected HomeTemplate() {
            Logger.getLogger(HomeTemplate.class.getName()).log(CustomLevel.MEDIUM, "Preparing HomeTemplate...");
            this.html = new Html();
            this.head = new Head();
            this.title = (Title) new Title().append(new Text("blink"));
            this.reset = (StyleElement) new StyleElement().append(new Text(new NoHeightReset().getReset()));
            this.pageStyle = (StyleElement) new StyleElement().append(new Text("html" + "{" + new BackgroundColor(240, 240, 240, 0.5).print() + new BackgroundImage(ImageValue.png, Base64Backgrounds.getFabric1LightFull()).print() + "}"));
            this.minWidthMediaQuery = (StyleElement) new StyleElement().append(new Text("@media screen and (min-width:600px) {#content {background-color:white;}}"));
            this.maxWidthMediaQuery = (StyleElement) new StyleElement().append(new Text("@media screen and (max-width:599px) {#content {background-color:blue;}}"));
            this.searchBoxToggle = new SearchBoxToggle("searchResults", "searchBar", ToggleValue.display);
            this.ajax = new Ajax("searchResults", MethodValue.GET, "search", new AbstractMap.SimpleEntry<>("query", "searchBar"));
            this.oninput = new Oninput(this.searchBoxToggle.getCall() + " " + this.ajax.getCall());
            this.searchBar = (Input) new Input().append(new Id("searchBar")).append(new Style(new MarginTop(80, MarginTopValue.pixels), new PaddingLeft(10, PaddingLeftValue.pixels), new Height(30, HeightValue.pixels), new Width(100, WidthValue.percent), new Border(0, BorderWidthValue.pixels))).append(oninput);
            this.body = new Body();
            this.responsiveHeader = (Div) new Div().append(new Style(new BackgroundColor(0, 0, 0, 1.0), new BackgroundImage(ImageValue.png, Base64Backgrounds.getFabric1LightFull()), new Color(ColorNameValue.White), new Width(100, WidthValue.percent), new Height(200, HeightValue.pixels)));
            this.resultsBox = (Div) new Div().append(new Id("searchResults")).append(new Style(new Color(ColorNameValue.Black), new Position(PositionValue.relative), new Zindex(1), new Width(270, WidthValue.pixels), new Display(DisplayValue.none), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto)));
            this.script = (Script) new Script().append(new ScriptType("text/javascript")).append(new Text(ajax.getFunction())).append(new Text(searchBoxToggle.getFunction()));
            this.searchDiv = (Div) new Div().append(new Style(new Width(270, WidthValue.pixels), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto)));
            this.header = (Div) new Div().append(new Style(new BackgroundColor(ColorNameValue.Black), new BackgroundImage(ImageValue.png, Base64Backgrounds.getFabric1LightFull()), new Color(ColorNameValue.White), new Width(100, WidthValue.percent), new Height(200, HeightValue.pixels)));
            this.main = (Div) new Div().append(new Id("main")).append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent), new MarginBottom(10, MarginBottomValue.pixels), new MaxWidth(960, MaxWidthValue.pixels), new MarginTop(30, MarginTopValue.pixels), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto), new BackgroundColor(BackgroundColorValue.transparent)));
            Div temp = (Div) new Div().append(new Id("about")).append(new Style(new Width(100, WidthValue.percent), new MinHeight(200, MinHeightValue.pixels), new MarginBottom(10, MarginBottomValue.pixels), new MarginTop(10, MarginTopValue.pixels), new MaxWidth(960, MaxWidthValue.pixels), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto), new BackgroundColor(120, 170, 255, 0.1), new Padding(15, PaddingValue.pixels))).append((Div) new Div().append(new Text("about")).append(new Style(new FontWeight(700)))).append(new Div().append(new Style(new MarginTop(20, MarginTopValue.pixels))).append(new Text(HttpRequests.sendGet("https://raw.githubusercontent.com/ThreaT/blink/master/README.md").getPayload())));
            this.about = temp;
            this.apps = (Div) new Div().append(new Id("apps")).append(new Style(new MarginRight(MarginRightValue.auto), new MarginLeft(MarginLeftValue.auto), new Width(990, WidthValue.pixels)));
            this.app1 = (Div) new Div().append(new Id("app1")).append(new Onclick("location.href='/htmltofront'")).append(new Style(new Width(45, WidthValue.percent), new Cursor(CursorValue.pointer), new Height(200, HeightValue.pixels), new MinHeight(200, MinHeightValue.pixels), new MarginBottom(20, MarginBottomValue.pixels), new MarginTop(10, MarginTopValue.pixels), new MaxWidth(960, MaxWidthValue.pixels), new Float(FloatValue.left), new BackgroundColor(120, 170, 255, 0.1), new Padding(15, PaddingValue.pixels))).append(new Div().append(new Style(new Height(70, HeightValue.percent), new Width(25, WidthValue.percent), new Padding(20, PaddingValue.pixels), new MarginTop(2, MarginTopValue.percent), new MarginLeft(5, MarginLeftValue.percent), new Float(FloatValue.left))).append(new Div().append(new Style(new Width(100, WidthValue.percent), new Height(100, HeightValue.percent), new BackgroundRepeat(BackgroundRepeatValue.no_repeat), new BackgroundSize(BackgroundSizeValue.cover), new BackgroundImage(ImageValue.png, "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAMAAACdt4HsAAABEVBMVEVfxbfaj1H01YH///9fxbdfxbdfxbdfxbdfxbfaj1Ffxbfaj1Ffxbf01YH01YFfxbf01YH01YH01YH01YH01YH01YH01YFfxbf01YFfxbdfxbdfxbdfxbdfxbdfxbdfxbdfxbdfxbdfxbdfxbfaj1Haj1H01YH01YFfxbf01YH01YH01YH01YH01YFfxbf01YH01YH01YFfxbdfxbdfxbfaj1Haj1Haj1Haj1FfxbdfxbdfxbdfxbfNsmvOs2vOtGzPtGzRtW3Rtm7St27UuG/UuW/VunDWu3DXu3HYu3HZvXLaj1HavnLfwnXixXfkx3jlx3jnyXnnyXroynru0H7v0H7w0X7x0n/y04Dz04D01YHy0WzsAAAAPHRSTlMAAAAAAQYVICIlJyc4Ozw9PT4/QEJDRUdHSl5jZWZnc5SfoaWsrbm6vLy9vr/AwcHDxcvR3+/w8fP3+fxew9meAAABcklEQVR42u3VSVPCQBCG4UlwQxTFuG+4gBvgDm5pAsYNkEVUMPP/f4hT05LxwpQ17cGyeA+p6gNPfRcSFumfZUVThUIqallWpG9aIJavifIxYyBdk6WNgQsELo2B2lcD4M8DtqYeYGtiWW6ag4BjDBwhYBMmIDBjPgEBwoRZCRAmHCNAmDCHQIIyQQBqwl25ywPfD3i3fK+9wuYlEE7wocnbAC+8BbfaK+wEATuDZx2eeBWgyitQ116qRQGoCe9Q7HieV+wU4U1/qQkIOBx7hApUn8XjQX+pdhDIcawFAO1X8WjqL9UkUwNEgQdeEJTA+9Beql2b4YBezVKD84Z4aC9VXAION23flkDOGIgLgDLggPp3npaA+YAM9ZWWIL5Us7/zWqd+WOjA4PM+AP4pwL5FAbY3pmjAmuueLVGAkRvXdbcIADt0RRQgSQXGScDo8ua5KRD+2BgYHls5JQGiodUrAiBbuCYCbJ0KsD0qMPFT4BPeO1n5ZwjdXQAAAABJRU5ErkJggg=="))))).append(new Div().append(new Style(new Height(70, HeightValue.percent), new Width(45, WidthValue.percent), new Padding(20, PaddingValue.pixels), new MarginTop(2, MarginTopValue.percent), new Float(FloatValue.left))).append(new Div().append(new Style(new MarginBottom(5, MarginBottomValue.pixels))).append(new Div().append(new Text("I'm more comfortable with plain html coding but want the benefits of Front.")))));
            this.app2 = (Div) new Div().append(new Id("app2")).append(new Onclick("location.href='/scenariocreator'")).append(new Style(new Width(45, WidthValue.percent), new Cursor(CursorValue.pointer), new Height(200, HeightValue.pixels), new MarginBottom(20, MarginBottomValue.pixels), new MarginTop(10, MarginTopValue.pixels), new MaxWidth(960, MaxWidthValue.pixels), new cool.blink.front.html.property.Float(FloatValue.right), new BackgroundColor(120, 170, 255, 0.1), new Padding(15, PaddingValue.pixels))).append(new Div().append(new Style(new Height(70, HeightValue.percent), new Width(25, WidthValue.percent), new BackgroundImage(ImageValue.png, "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"), new Padding(20, PaddingValue.pixels), new MarginTop(2, MarginTopValue.percent), new MarginLeft(5, MarginLeftValue.percent), new cool.blink.front.html.property.Float(FloatValue.left)))).append(new Div().append(new Style(new Height(70, HeightValue.percent), new Width(45, WidthValue.percent), new Padding(20, PaddingValue.pixels), new MarginTop(2, MarginTopValue.percent), new cool.blink.front.html.property.Float(FloatValue.left))).append(new Div().append(new Style(new MarginBottom(5, MarginBottomValue.pixels))).append(new Div().append(new Text("I have all the necessary details but I don't have time to code each scenario manually.")))));
            this.document = new Document().append(
                    this.html.append(
                            this.head.append(
                                    this.title
                            ).append(
                                    this.reset
                            ).append(
                                    this.pageStyle
                            ).append(
                                    this.minWidthMediaQuery
                            ).append(
                                    this.maxWidthMediaQuery
                            )
                    ).append(
                            this.body.append(
                                    this.header.append(
                                            this.searchDiv.append(
                                                    this.searchBar
                                            )
                                    ).append(
                                            this.resultsBox
                                    )
                            ).append(
                                    this.main.append(
                                            this.about
                                    )
                                    .append(
                                            this.apps.append(
                                                    this.app1
                                            ).append(
                                                    this.app2
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

        public StyleElement getPageStyle() {
            return pageStyle;
        }

        public StyleElement getMinWidthMediaQuery() {
            return minWidthMediaQuery;
        }

        public StyleElement getMaxWidthMediaQuery() {
            return maxWidthMediaQuery;
        }

        public Script getScript() {
            return script;
        }

        public Ajax getAjax() {
            return ajax;
        }

        public Input getSearchBar() {
            return searchBar;
        }

        public Body getBody() {
            return body;
        }

        public Div getResponsiveHeader() {
            return responsiveHeader;
        }

        public Div getResultsBox() {
            return resultsBox;
        }

        public SearchBoxToggle getSearchBoxToggle() {
            return searchBoxToggle;
        }

        public Oninput getOninput() {
            return oninput;
        }

        public Div getSearchDiv() {
            return searchDiv;
        }

        public Div getHeader() {
            return header;
        }

        public Div getMain() {
            return main;
        }

        public Div getAbout() {
            return about;
        }

        public Div getApps() {
            return apps;
        }

        public Div getApp1() {
            return app1;
        }

        public Div getApp2() {
            return app2;
        }

    }

    @Override
    public String toString() {
        return "Home{" + super.toString() + '}';
    }

}
