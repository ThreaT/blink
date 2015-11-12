package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Size extends Attribute {

    public Size() {
        super(new FrontContent(""));
    }

    public Size(final FrontContent frontContent) {
        super(frontContent);
    }

    public Size(final String frontContent) {
        super(new FrontContent(frontContent));
    }

    public Size(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
