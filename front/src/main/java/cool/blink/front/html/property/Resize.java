package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.ResizeValue;

public class Resize extends Property {

    public Resize(ResizeValue resizeValue) {
        super(new FrontContent(resizeValue.toString()));
    }

}
