package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.VerticalAlignValue;

public class VerticalAlign extends Property {

    public VerticalAlign(VerticalAlignValue verticalAlign) {
        super(new FrontContent("vertical-align"), new FrontContent(verticalAlign.toString()));
    }

}
