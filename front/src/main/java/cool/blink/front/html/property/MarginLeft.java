package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MarginLeftValue;

public class MarginLeft extends Property {

    public MarginLeft(MarginLeftValue marginLeftValue) {
        super(new FrontContent("margin-left"), new FrontContent(marginLeftValue.toString()));
    }

    public MarginLeft(Integer amount, MarginLeftValue marginLeftValue) {
        super(new FrontContent("margin-left"), new FrontContent(amount.toString() + marginLeftValue.toString()));
    }

}
