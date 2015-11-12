package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BackgroundColorValue;
import cool.blink.front.html.property.value.ColorNameValue;

public class BackgroundColor extends Property {

    public BackgroundColor(ColorNameValue colorNameValue) {
        super(new FrontContent("background-color"), new FrontContent(colorNameValue.toString()));
    }

    public BackgroundColor(BackgroundColorValue backgroundColorValue) {
        super(new FrontContent("background-color"), new FrontContent(backgroundColorValue.toString()));
    }

    public BackgroundColor(Integer red, Integer green, Integer blue, Double alpha) {
        super(new FrontContent("background-color"), new FrontContent("rgba(" + red + ", " + green + ", " + blue + ", " + alpha + ")"));
    }

}
