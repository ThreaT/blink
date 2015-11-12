package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.FloatValue;

public class Float extends Property {

    public Float(FloatValue floatValue) {
        super(new FrontContent(floatValue.toString()));
    }

}
