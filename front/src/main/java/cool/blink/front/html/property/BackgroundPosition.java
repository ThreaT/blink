package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BackgroundPositionValue;

public class BackgroundPosition extends Property {

    public BackgroundPosition(BackgroundPositionValue backgroundPositionValue) {
        super(new FrontContent("background-position"), new FrontContent(backgroundPositionValue.toString()));
    }

}
