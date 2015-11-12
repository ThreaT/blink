package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.PositionValue;

public class Position extends Property {

    public Position(PositionValue positionValue) {
        super(new FrontContent(positionValue.toString()));
    }

}
