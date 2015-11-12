package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.ColorNameValue;

public class Color extends Property {

    public Color(Integer red, Integer green, Integer blue, Integer alpha) {
        super(new FrontContent("rgba(" + red + ", " + green + ", " + blue + ", " + alpha + ")"));
    }

    public Color(ColorNameValue colorNameValue) {
        super(new FrontContent(colorNameValue.toString()));
    }

}
