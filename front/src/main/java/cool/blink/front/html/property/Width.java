package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.WidthValue;

public class Width extends Property {

    public Width(Integer amount, WidthValue widthValue) {
        super(new FrontContent(amount.toString() + widthValue.toString()));
    }

}
