package cool.blink.front.template.html;

import cool.blink.front.html.element.A;
import cool.blink.front.html.element.Abbr;
import cool.blink.front.html.element.Acronym;
import cool.blink.front.html.element.Address;
import cool.blink.front.html.element.Applet;
import cool.blink.front.html.element.Article;
import cool.blink.front.html.element.Aside;
import cool.blink.front.html.element.Audio;
import cool.blink.front.html.element.B;
import cool.blink.front.html.element.Big;
import cool.blink.front.html.element.Blockquote;
import cool.blink.front.html.element.Body;
import cool.blink.front.html.element.Canvas;
import cool.blink.front.html.element.Caption;
import cool.blink.front.html.element.Center;
import cool.blink.front.html.element.Cite;
import cool.blink.front.html.element.Code;
import cool.blink.front.html.element.Dd;
import cool.blink.front.html.element.Del;
import cool.blink.front.html.element.Details;
import cool.blink.front.html.element.Dfn;
import cool.blink.front.html.element.Div;
import cool.blink.front.html.element.Dl;
import cool.blink.front.html.element.Dt;
import cool.blink.front.html.element.Em;
import cool.blink.front.html.element.Embed;
import cool.blink.front.html.element.Fieldset;
import cool.blink.front.html.element.Figcaption;
import cool.blink.front.html.element.Figure;
import cool.blink.front.html.element.Footer;
import cool.blink.front.html.element.Form;
import cool.blink.front.html.element.H1;
import cool.blink.front.html.element.H2;
import cool.blink.front.html.element.H3;
import cool.blink.front.html.element.H4;
import cool.blink.front.html.element.H5;
import cool.blink.front.html.element.H6;
import cool.blink.front.html.element.Header;
import cool.blink.front.html.element.Hgroup;
import cool.blink.front.html.element.Html;
import cool.blink.front.html.element.I;
import cool.blink.front.html.element.Iframe;
import cool.blink.front.html.element.Img;
import cool.blink.front.html.element.Ins;
import cool.blink.front.html.element.Kbd;
import cool.blink.front.html.element.Label;
import cool.blink.front.html.element.Legend;
import cool.blink.front.html.element.Li;
import cool.blink.front.html.element.Mark;
import cool.blink.front.html.element.Menu;
import cool.blink.front.html.element.Nav;
import cool.blink.front.html.element.Ol;
import cool.blink.front.html.element.Output;
import cool.blink.front.html.element.P;
import cool.blink.front.html.element.Pre;
import cool.blink.front.html.element.Q;
import cool.blink.front.html.element.Ruby;
import cool.blink.front.html.element.S;
import cool.blink.front.html.element.Samp;
import cool.blink.front.html.element.Section;
import cool.blink.front.html.element.Small;
import cool.blink.front.html.element.Span;
import cool.blink.front.html.element.Strike;
import cool.blink.front.html.element.Strong;
import cool.blink.front.html.element.Sub;
import cool.blink.front.html.element.Summary;
import cool.blink.front.html.element.Sup;
import cool.blink.front.html.element.Table;
import cool.blink.front.html.element.Tbody;
import cool.blink.front.html.element.Td;
import cool.blink.front.html.element.Tfoot;
import cool.blink.front.html.element.Th;
import cool.blink.front.html.element.Thead;
import cool.blink.front.html.element.Time;
import cool.blink.front.html.element.Tr;
import cool.blink.front.html.element.Tt;
import cool.blink.front.html.element.U;
import cool.blink.front.html.element.Ul;
import cool.blink.front.html.element.Var;
import cool.blink.front.html.element.Video;
import cool.blink.front.html.property.Border;
import cool.blink.front.html.property.BorderCollapse;
import cool.blink.front.html.property.BorderSpacing;
import cool.blink.front.html.property.Content;
import cool.blink.front.html.property.Display;
import cool.blink.front.html.property.Font;
import cool.blink.front.html.property.FontSize;
import cool.blink.front.html.property.Height;
import cool.blink.front.html.property.LineHeight;
import cool.blink.front.html.property.ListStyle;
import cool.blink.front.html.property.Margin;
import cool.blink.front.html.property.Padding;
import cool.blink.front.html.property.Quotes;
import cool.blink.front.html.property.VerticalAlign;
import cool.blink.front.html.property.value.BorderCollapseValue;
import cool.blink.front.html.property.value.BorderSpacingValue;
import cool.blink.front.html.property.value.BorderWidthValue;
import cool.blink.front.html.property.value.ContentValue;
import cool.blink.front.html.property.value.DisplayValue;
import cool.blink.front.html.property.value.FontSizeValue;
import cool.blink.front.html.property.value.FontValue;
import cool.blink.front.html.property.value.HeightValue;
import cool.blink.front.html.property.value.ListStyleValue;
import cool.blink.front.html.property.value.MarginValue;
import cool.blink.front.html.property.value.PaddingValue;
import cool.blink.front.html.property.value.QuoteValue;
import cool.blink.front.html.property.value.VerticalAlignValue;

public final class Reset {

    private final String reset;

    public Reset() {
        String mainClasses = (Html.class.getSimpleName() + ","
                + Body.class.getSimpleName() + ","
                + Div.class.getSimpleName() + ","
                + Span.class.getSimpleName() + ","
                + Applet.class.getSimpleName() + ","
                + Object.class.getSimpleName() + ","
                + Iframe.class.getSimpleName() + ","
                + H1.class.getSimpleName() + ","
                + H2.class.getSimpleName() + ","
                + H3.class.getSimpleName() + ","
                + H4.class.getSimpleName() + ","
                + H5.class.getSimpleName() + ","
                + H6.class.getSimpleName() + ","
                + P.class.getSimpleName() + ","
                + Blockquote.class.getSimpleName() + ","
                + Pre.class.getSimpleName() + ","
                + A.class.getSimpleName() + ","
                + Abbr.class.getSimpleName() + ","
                + Acronym.class.getSimpleName() + ","
                + Address.class.getSimpleName() + ","
                + Big.class.getSimpleName() + ","
                + Cite.class.getSimpleName() + ","
                + Code.class.getSimpleName() + ","
                + Del.class.getSimpleName() + ","
                + Dfn.class.getSimpleName() + ","
                + Em.class.getSimpleName() + ","
                + Img.class.getSimpleName() + ","
                + Ins.class.getSimpleName() + ","
                + Kbd.class.getSimpleName() + ","
                + Q.class.getSimpleName() + ","
                + S.class.getSimpleName() + ","
                + Samp.class.getSimpleName() + ","
                + Small.class.getSimpleName() + ","
                + Strike.class.getSimpleName() + ","
                + Strong.class.getSimpleName() + ","
                + Sub.class.getSimpleName() + ","
                + Sup.class.getSimpleName() + ","
                + Tt.class.getSimpleName() + ","
                + Var.class.getSimpleName() + ","
                + B.class.getSimpleName() + ","
                + U.class.getSimpleName() + ","
                + I.class.getSimpleName() + ","
                + Center.class.getSimpleName() + ","
                + Dl.class.getSimpleName() + ","
                + Dt.class.getSimpleName() + ","
                + Dd.class.getSimpleName() + ","
                + Ol.class.getSimpleName() + ","
                + Ul.class.getSimpleName() + ","
                + Li.class.getSimpleName() + ","
                + Fieldset.class.getSimpleName() + ","
                + Form.class.getSimpleName() + ","
                + Label.class.getSimpleName() + ","
                + Legend.class.getSimpleName() + ","
                + Table.class.getSimpleName() + ","
                + Caption.class.getSimpleName() + ","
                + Tbody.class.getSimpleName() + ","
                + Tfoot.class.getSimpleName() + ","
                + Thead.class.getSimpleName() + ","
                + Tr.class.getSimpleName() + ","
                + Th.class.getSimpleName() + ","
                + Td.class.getSimpleName() + ","
                + Article.class.getSimpleName() + ","
                + Aside.class.getSimpleName() + ","
                + Canvas.class.getSimpleName() + ","
                + Details.class.getSimpleName() + ","
                + Embed.class.getSimpleName() + ","
                + Figure.class.getSimpleName() + ","
                + Figcaption.class.getSimpleName() + ","
                + Footer.class.getSimpleName() + ","
                + Header.class.getSimpleName() + ","
                + Hgroup.class.getSimpleName() + ","
                + Menu.class.getSimpleName() + ","
                + Nav.class.getSimpleName() + ","
                + Output.class.getSimpleName() + ","
                + Ruby.class.getSimpleName() + ","
                + Section.class.getSimpleName() + ","
                + Summary.class.getSimpleName() + ","
                + Time.class.getSimpleName() + ","
                + Mark.class.getSimpleName() + ","
                + Audio.class.getSimpleName() + ","
                + Video.class.getSimpleName()).toLowerCase();
        String mainProperties = new Margin(0, MarginValue.blank).print() + new Padding(0, PaddingValue.blank).print() + new Border(0, BorderWidthValue.blank).print() + new FontSize(100, FontSizeValue.percent).print() + new Font(FontValue.inherit).print() + new VerticalAlign(VerticalAlignValue.baseline).print() + new Height(100, HeightValue.percent).print();

        String bodyClass = Body.class.getSimpleName().toLowerCase();
        String bodyProperties = new LineHeight(Integer.toString(1)).print();

        String blockClasses
                = (Article.class.getSimpleName() + ", "
                + Aside.class.getSimpleName() + ", "
                + Details.class.getSimpleName() + ", "
                + Figcaption.class.getSimpleName() + ", "
                + Figure.class.getSimpleName() + ", "
                + Footer.class.getSimpleName() + ", "
                + Header.class.getSimpleName() + ", "
                + Hgroup.class.getSimpleName() + ", "
                + Menu.class.getSimpleName() + ", "
                + Nav.class.getSimpleName() + ", "
                + Section.class.getSimpleName()).toLowerCase();
        String blockProperties = new Display(DisplayValue.block).print();

        String listStyleClasses = Ol.class.getSimpleName().toLowerCase() + ", " + Ul.class.getSimpleName().toLowerCase();
        String listStyleProperties = new ListStyle(ListStyleValue.none).print();

        String quoteClasses = Blockquote.class.getSimpleName().toLowerCase() + ", " + Q.class.getSimpleName().toLowerCase();
        String quoteProperties = new Quotes(QuoteValue.none).print();

        String blockQuoteClasses
                = Blockquote.class.getSimpleName().toLowerCase() + ":before, "
                + Blockquote.class.getSimpleName().toLowerCase() + ":after, "
                + Q.class.getSimpleName().toLowerCase() + ":before, "
                + Q.class.getSimpleName().toLowerCase() + ":after";
        String blockQuoteProperties = new Content(ContentValue.blank).print() + new Content(ContentValue.none).print();

        String tableClass = Table.class.getSimpleName().toLowerCase();
        String tableProperties = new BorderCollapse(BorderCollapseValue.collapse).print() + new BorderSpacing(0, BorderSpacingValue.blank).print();

        this.reset = mainClasses + "{" + mainProperties + "}"
                + blockClasses + "{" + blockProperties + "}"
                + bodyClass + "{" + bodyProperties + "}"
                + listStyleClasses + "{" + listStyleProperties + "}"
                + quoteClasses + "{" + quoteProperties + "}"
                + blockQuoteClasses + "{" + blockQuoteProperties + "}"
                + tableClass + "{" + tableProperties + "}";
    }

    public final String getReset() {
        return reset;
    }

}
