package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BorderCollapseValue;

public class BorderCollapse extends Property {

    public BorderCollapse(BorderCollapseValue borderCollapseValue) {
        super(new FrontContent("border-collapse"), new FrontContent(borderCollapseValue.toString()));
    }

}
