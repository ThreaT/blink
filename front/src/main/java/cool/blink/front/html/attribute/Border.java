package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Border extends Attribute {

    public Border() {
        super(new FrontContent(""));
    }

    public Border(final FrontContent frontContent) {
        super(frontContent);
    }

    public Border(final String frontContent) {
        super(new FrontContent(frontContent));
    }

    public Border(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
