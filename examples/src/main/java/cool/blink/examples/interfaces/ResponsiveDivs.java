package cool.blink.examples.interfaces;

import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Id;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Doctype;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.StyleElement;
import cool.blink.front.html.property.BackgroundColor;
import cool.blink.front.html.property.Color;
import cool.blink.front.html.property.Float;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.MarginLeft;
import cool.blink.front.html.property.MarginRight;
import cool.blink.front.html.property.MaxWidth;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.ColorNameValue;
import cool.blink.front.html.property.value.DoctypeValue;
import cool.blink.front.html.property.value.FloatValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.MarginLeftValue;
import cool.blink.front.html.property.value.MarginRightValue;
import cool.blink.front.html.property.value.MaxWidthValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.front.css.template.Reset;

public class ResponsiveDivs {

    public static void main(String args[]) {
        Doctype doctype = new Doctype(DoctypeValue.HTML5);
        Html html = new Html();
        Head head = new Head();
        StyleElement reset = (StyleElement) new StyleElement().append(new Text(new Reset().getReset()));
        StyleElement minWidthMediaQuery = (StyleElement) new StyleElement().append(new Text("@media screen and (min-width:600px) {#content {background-color:red;}}"));
        StyleElement maxWidthMediaQuery = (StyleElement) new StyleElement().append(new Text("@media screen and (max-width:599px) {#content {background-color:blue;}}"));
        Body body = new Body();
        Div div1 = (Div) new Div().append(new Style(new BackgroundColor(ColorNameValue.Black), new Color(ColorNameValue.White), new Width(100, WidthValue.percent), new Height(200, HeightValue.pixels)));
        Div div2 = (Div) new Div().append(new Id("content")).append(new Style(new Width(100, WidthValue.percent), new Height(500, HeightValue.pixels), new MaxWidth(800, MaxWidthValue.pixels), new MarginLeft(MarginLeftValue.auto), new MarginRight(MarginRightValue.auto)));
        Div div3 = (Div) new Div().append(new Id("div3")).append(new Style(new Width(200, WidthValue.pixels), new Height(200, HeightValue.pixels), new BackgroundColor(ColorNameValue.Yellow), new Float(FloatValue.left)));
        Div div4 = (Div) new Div().append(new Id("div4")).append(new Style(new Width(200, WidthValue.pixels), new Height(200, HeightValue.pixels), new BackgroundColor(ColorNameValue.Yellow), new Float(FloatValue.left)));
        Document document = new Document()
                .append(
                        doctype
                ).append(
                        html.append(
                                head.append(
                                        reset
                                ).append(
                                        minWidthMediaQuery
                                ).append(
                                        maxWidthMediaQuery
                                )
                        ).append(
                                body.append(
                                        div1
                                ).append(
                                        div2.append(
                                                div3
                                        ).append(
                                                div4
                                        )
                                )
                        )
                );
        System.out.println(document.print());
    }
}
