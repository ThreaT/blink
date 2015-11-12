package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MinWidthValue;

public class MinWidth extends Property {

    public MinWidth(Integer amount, MinWidthValue minWidthValue) {
        super(new FrontContent("min-width"), new FrontContent(amount.toString() + minWidthValue.toString()));
    }

}
