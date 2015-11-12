package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.LeftValue;

public class Left extends Property {

    public Left(Integer amount, LeftValue leftValue) {
        super(new FrontContent(amount + leftValue.toString()));
    }

}
