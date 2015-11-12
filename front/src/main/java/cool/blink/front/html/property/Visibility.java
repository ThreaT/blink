package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.VisibilityValue;

public class Visibility extends Property {

    public Visibility(VisibilityValue visibilityValue) {
        super(new FrontContent(visibilityValue.toString()));
    }

}
