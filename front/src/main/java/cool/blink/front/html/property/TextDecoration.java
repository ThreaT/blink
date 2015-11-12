package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.TextDecorationValue;

public class TextDecoration extends Property {

    public TextDecoration(TextDecorationValue textDecorationValue) {
        super(new FrontContent("text-decoration"), new FrontContent(textDecorationValue.toString()));
    }

}
