package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MethodValue;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Method extends Attribute {

    public Method() {
        super(new FrontContent(""));
    }

    public Method(final FrontContent frontContent) {
        super(frontContent);
    }

    public Method(final MethodValue methodValue) {
        super(new FrontContent(methodValue.toString()));
    }

    public Method(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
