package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MarginTopValue;

public class MarginTop extends Property {

    public MarginTop(MarginTopValue marginTopValue) {
        super(new FrontContent("margin-top"), new FrontContent(marginTopValue.toString()));
    }

    public MarginTop(Integer amount, MarginTopValue marginTopValue) {
        super(new FrontContent("margin-top"), new FrontContent(amount.toString() + marginTopValue.toString()));
    }

}
