package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MarginRightValue;

public class MarginRight extends Property {

    public MarginRight(Integer amount, MarginRightValue marginRightValue) {
        super(new FrontContent("margin-right"), new FrontContent(amount + marginRightValue.toString()));
    }

    public MarginRight(MarginRightValue marginRightValue) {
        super(new FrontContent("margin-right"), new FrontContent(marginRightValue.toString()));
    }

}
