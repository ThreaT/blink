package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.DisplayValue;

public class Display extends Property {

    public Display(DisplayValue display) {
        super(new FrontContent(display.toString()));
    }

}
