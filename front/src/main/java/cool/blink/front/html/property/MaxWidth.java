package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MaxWidthValue;

public class MaxWidth extends Property {

    public MaxWidth(Integer amount, MaxWidthValue maxWidthValue) {
        super(new FrontContent("max-width"), new FrontContent(amount.toString() + maxWidthValue.toString()));
    }

}
