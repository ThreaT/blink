package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.LineHeightValue;

public class LineHeight extends Property {
    
    public LineHeight(String lineHeightValue) {
        super(new FrontContent("line-height"), new FrontContent(lineHeightValue));
    }

    public LineHeight(LineHeightValue lineHeightValue) {
        super(new FrontContent("line-height"), new FrontContent(lineHeightValue.toString()));
    }
}
