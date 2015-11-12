package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Href extends Attribute {

    public Href() {
        super(new FrontContent(""));
    }

    public Href(final FrontContent frontContent) {
        super(frontContent);
    }

    public Href(final String frontContent) {
        super(new FrontContent(frontContent));
    }

    public Href(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
