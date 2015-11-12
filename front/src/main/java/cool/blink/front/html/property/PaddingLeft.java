package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.PaddingLeftValue;

public class PaddingLeft extends Property {

    public PaddingLeft(Integer amount, PaddingLeftValue paddingLeftValue) {
        super(new FrontContent("padding-left"), new FrontContent(amount.toString() + paddingLeftValue.toString()));
    }

}
