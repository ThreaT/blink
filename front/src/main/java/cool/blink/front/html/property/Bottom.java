package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BottomValue;

public class Bottom extends Property {

    public Bottom(Integer amount, BottomValue bottomValue) {
        super(new FrontContent(amount + bottomValue.toString()));
    }

}
