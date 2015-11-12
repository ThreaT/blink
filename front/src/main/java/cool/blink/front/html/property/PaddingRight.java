package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.PaddingRightValue;

public class PaddingRight extends Property {

    public PaddingRight(Integer amount, PaddingRightValue paddingRightValue) {
        super(new FrontContent("padding-right"), new FrontContent(amount.toString() + paddingRightValue.toString()));
    }

}
