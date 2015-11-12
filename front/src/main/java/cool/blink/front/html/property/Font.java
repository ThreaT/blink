package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.FontFamilyValue;
import cool.blink.front.html.property.value.FontSizeValue;
import cool.blink.front.html.property.value.FontValue;

public class Font extends Property {

    public Font(FontValue fontValue) {
        super(new FrontContent(fontValue.toString()));
    }

    public Font(Integer amount, FontSizeValue fontSizeValue, String fontFamily) {
        super(new FrontContent(fontFamily));
    }

    public Font(Integer amount, FontSizeValue fontSizeValue, FontFamilyValue fontFamilyValue) {
        super(new FrontContent(fontFamilyValue.toString()));
    }

}
