package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.ClearValue;

public class Clear extends Property {

    public Clear(ClearValue clearValue) {
        super(new FrontContent(clearValue.toString()));
    }

}
