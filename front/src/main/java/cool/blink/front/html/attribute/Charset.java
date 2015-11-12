package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Charset extends Attribute {

    public Charset() {
        super(new FrontContent(""));
    }

    public Charset(final FrontContent frontContent) {
        super(frontContent);
    }

    public Charset(final String frontContent) {
        super(new FrontContent(frontContent));
    }

    public Charset(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
