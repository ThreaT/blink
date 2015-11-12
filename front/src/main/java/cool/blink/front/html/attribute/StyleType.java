package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.property.value.StyleTypeValue;

public class StyleType extends Attribute {

    public StyleType(final StyleTypeValue styleTypeValue) {
        super("type", new FrontContent(styleTypeValue.toString()));
    }

    public StyleType(final String properties) {
        super("type", new FrontContent(properties));
    }

}
