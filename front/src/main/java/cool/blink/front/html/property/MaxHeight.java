package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MaxHeightValue;

public class MaxHeight extends Property {

    public MaxHeight(Integer amount, MaxHeightValue maxHeightValue) {
        super(new FrontContent("max-height"),new FrontContent(amount.toString() + maxHeightValue.toString()));
    }

}
