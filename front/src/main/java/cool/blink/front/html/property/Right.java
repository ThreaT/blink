package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.RightValue;

public class Right extends Property {

    public Right(Integer amount, RightValue rightValue) {
        super(new FrontContent(amount + rightValue.toString()));
    }

}
