package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.PaddingTopValue;

public class PaddingTop extends Property {

    public PaddingTop(Integer amount, PaddingTopValue paddingTopValue) {
        super(new FrontContent("padding-top"), new FrontContent(amount.toString() + paddingTopValue.toString()));
    }

}
