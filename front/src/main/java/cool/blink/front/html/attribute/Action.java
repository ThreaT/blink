package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Action extends Attribute {

    public Action() {
        super(new FrontContent(""));
    }

    public Action(final FrontContent frontContent) {
        super(frontContent);
    }

    public Action(final String frontContent) {
        super(new FrontContent(frontContent));
    }

    public Action(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
