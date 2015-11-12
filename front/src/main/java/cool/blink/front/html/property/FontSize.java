package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.FontSizeValue;

public class FontSize extends Property {

    public FontSize(Integer amount, FontSizeValue fontSizeValue) {
        super(new FrontContent("font-size"), new FrontContent(amount.toString() + fontSizeValue.toString()));
    }

}
