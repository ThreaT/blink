package cool.blink.front.template;

import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Id;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.attribute.StyleType;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Doctype;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.StyleElement;
import cool.blink.front.html.property.BackgroundColor;
import cool.blink.front.html.property.Border;
import cool.blink.front.html.property.BorderRight;
import cool.blink.front.html.property.Bottom;
import cool.blink.front.html.property.Color;
import cool.blink.front.html.property.Float;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.Left;
import cool.blink.front.html.property.MarginBottom;
import cool.blink.front.html.property.MarginLeft;
import cool.blink.front.html.property.MarginRight;
import cool.blink.front.html.property.MarginTop;
import cool.blink.front.html.property.Position;
import cool.blink.front.html.property.Top;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.BorderStyleValue;
import cool.blink.front.html.property.value.BorderWidthValue;
import cool.blink.front.html.property.value.BottomValue;
import cool.blink.front.html.property.value.ColorNameValue;
import cool.blink.front.html.property.value.DoctypeValue;
import cool.blink.front.html.property.value.FloatValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.LeftValue;
import cool.blink.front.html.property.value.MarginBottomValue;
import cool.blink.front.html.property.value.MarginLeftValue;
import cool.blink.front.html.property.value.MarginRightValue;
import cool.blink.front.html.property.value.MarginTopValue;
import cool.blink.front.html.property.value.PositionValue;
import cool.blink.front.html.property.value.TopValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.front.template.html.Reset;
import org.joda.time.DateTime;

/**
 * Full black and white responsive, offline-ready template (webstorage or
 * etags??)
 */
public class BlackAndWhite2Col {

    public static void main(String args[]) {
        BlackAndWhite2Col blackAndWhite = new BlackAndWhite2Col();
        System.out.println(blackAndWhite.document.print());
    }

    private final Document document;
    private final Doctype doctype;
    private final Html html;
    private final Head head;
    private final Reset reset;
    private final StyleElement resetStyles;
//    private final Offline offlineStorage;
//    private final Script offlineStorageScript;
    private final Body body;
    private final Div header;
    private final Div logo;
    private final Div contentOuter;
    private final Div contentLeft;
    private final Div leftBox;
    private final Div contentRight;
    private final Div rightBox;
    private final Div copyright;
    private final Div footer;

    public BlackAndWhite2Col() {
        this.doctype = new Doctype(DoctypeValue.HTML5);
        this.html = new Html();
        this.head = (Head) new Head();
        this.reset = new Reset();
        this.resetStyles = (StyleElement) new StyleElement().append(new StyleType("text/css")).append(new Text(this.reset.getReset()));
//        this.offlineStorage = new Offline();
//        this.offlineStorageScript = (Script) new Script().append(new ScriptType("text/javascript")).append(new Text(this.offlineStorage.getFunction()));
        this.body = (Body) new Body();//.append(new Onload(this.offlineStorage.getCall()));
        this.header = (Div) new Div().append(new Id("header")).append(new Style(new Top(0, TopValue.percent), new Position(PositionValue.absolute), new Width(100, WidthValue.percent), new Height(15, HeightValue.percent), new BackgroundColor(ColorNameValue.Black)));
        this.logo = (Div) new Div().append(new Id("logo")).append(new Style(new Width(10, WidthValue.percent), new Height(80, HeightValue.percent), new Border(1, BorderWidthValue.pixels, ColorNameValue.White, BorderStyleValue.solid), new Position(PositionValue.absolute), new Left(45, LeftValue.percent), new Top(10, TopValue.percent)));
        this.contentOuter = (Div) new Div().append(new Id("contentOuter")).append(new Style(new Position(PositionValue.absolute), new Width(70, WidthValue.percent), new Height(100, HeightValue.percent), new Top(20, TopValue.percent), new Left(15, LeftValue.percent)));
        this.contentLeft = (Div) new Div().append(new Id("contentLeft")).append(new Style(new BorderRight(1, BorderWidthValue.pixels, BorderStyleValue.solid, 242, 242, 242, 1.0), new Float(FloatValue.left), new Width(30, WidthValue.percent), new Height(100, HeightValue.percent)));
        this.contentRight = (Div) new Div().append(new Id("contentRight")).append(new Style(new Float(FloatValue.right), new Width(69, WidthValue.percent), new Height(100, HeightValue.percent)));
        this.leftBox = (Div) new Div().append(new Style(new Width(90, WidthValue.percent), new Height(20, HeightValue.percent), new Position(PositionValue.relative), new BackgroundColor(252, 252, 254, 1.0), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto), new MarginBottom(10, MarginBottomValue.percent)));
        this.rightBox = (Div) new Div().append(new Style(new Width(90, WidthValue.percent), new Height(20, HeightValue.percent), new Position(PositionValue.relative), new BackgroundColor(252, 252, 254, 1.0), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto), new MarginBottom(5, MarginBottomValue.percent)));
        this.footer = (Div) new Div().append(new Id("footer")).append(new Style(new Position(PositionValue.absolute), new Bottom(-40, BottomValue.percent), new Width(100, WidthValue.percent), new Height(15, HeightValue.percent), new BackgroundColor(ColorNameValue.Black)));
        this.copyright = (Div) new Div().append(new Style(new Color(ColorNameValue.White), new Position(PositionValue.relative), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto), new MarginTop(20, MarginTopValue.pixels), new Width(70, WidthValue.pixels), new Height(20, HeightValue.pixels))).append(new Text("&copy; " + new DateTime().getYear()));
        this.document = new Document().append(
                this.doctype
        ).append(
                this.html.append(
                        this.head
                        //.append(this.offlineStorageScript)
                        .append(
                                this.resetStyles
                        )
                ).append(
                        this.body.append(
                                this.header.append(
                                        this.logo
                                )
                        ).append(
                                this.contentOuter.append(
                                        this.contentLeft.append(
                                                this.leftBox
                                        ).append(
                                                this.leftBox
                                        ).append(
                                                this.leftBox
                                        )
                                ).append(
                                        this.contentRight.append(
                                                this.rightBox
                                        ).append(
                                                this.rightBox
                                        ).append(
                                                this.rightBox
                                        )
                                )
                        ).append(
                                this.footer.append(
                                        this.copyright
                                )
                        )
                )
        );
    }

}
