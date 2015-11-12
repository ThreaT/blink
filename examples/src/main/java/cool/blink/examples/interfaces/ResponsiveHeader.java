package cool.blink.examples.interfaces;

import cool.blink.front.Document;
import cool.blink.front.html.Text;
import cool.blink.front.html.attribute.Style;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Doctype;
import cool.blink.front.html.element.Head;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.StyleElement;
import cool.blink.front.html.property.BackgroundColor;
import cool.blink.front.html.property.Color;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.Width;
import cool.blink.front.html.property.value.ColorNameValue;
import cool.blink.front.html.property.value.DoctypeValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.WidthValue;
import cool.blink.front.template.html.Reset;

public class ResponsiveHeader {

    public static void main(String args[]) {
        Doctype doctype = new Doctype(DoctypeValue.HTML5);
        Html html = new Html();
        Head head = new Head();
        StyleElement reset = (StyleElement) new StyleElement().append(new Text(new Reset().getReset()));
        Body body = new Body();
        Div responsiveHeader = (Div) new Div().append(new Style(new BackgroundColor(ColorNameValue.Black), new Color(ColorNameValue.White), new Width(100, WidthValue.percent), new Height(200, HeightValue.pixels)));
        Document document = new Document()
                .append(
                        doctype
                ).append(
                        html.append(
                                head.append(
                                        reset
                                )
                        ).append(
                                body.append(
                                        responsiveHeader
                                )
                        )
                );
        System.out.println(document.print());
    }
}
