package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.TextAlignValue;

public class TextAlign extends Property {

    public TextAlign(TextAlignValue textAlignValue) {
        super(new FrontContent("text-align"), new FrontContent(textAlignValue.toString()));
    }

}
