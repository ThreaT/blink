package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BorderSpacingValue;

public class BorderSpacing extends Property {

    public BorderSpacing(Integer amount, BorderSpacingValue borderSpacingValue) {
        super(new FrontContent("border-spacing"), new FrontContent(amount.toString() + borderSpacingValue.toString()));
    }

}
