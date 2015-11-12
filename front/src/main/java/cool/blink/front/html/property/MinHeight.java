package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MinHeightValue;

public class MinHeight extends Property {

    public MinHeight(Integer amount, MinHeightValue minHeightValue) {
        super(new FrontContent("min-height"), new FrontContent(amount.toString() + minHeightValue.toString()));
    }

}
