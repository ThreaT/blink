package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MarginBottomValue;

public class MarginBottom extends Property {

    public MarginBottom(Integer amount, MarginBottomValue marginBottomValue) {
        super(new FrontContent("margin-bottom"), new FrontContent(amount.toString() + marginBottomValue.toString()));
    }

}
