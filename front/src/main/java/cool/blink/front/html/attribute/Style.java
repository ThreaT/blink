package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Style extends Attribute {

    public Style() {
        super(new FrontContent(""));
    }

    public Style(final FrontContent content) {
        super(content);
    }

    public Style(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }

}
