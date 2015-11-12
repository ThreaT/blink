package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Onclick extends Attribute {

    public Onclick() {
        super(new FrontContent(""));
    }
    
    public Onclick(final String action) {
        super(new FrontContent(action));
    }

    public Onclick(final FrontContent content) {
        super(content);
    }

    public Onclick(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
