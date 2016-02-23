package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Onchange extends Attribute {

    public Onchange() {
        super(new FrontContent(""));
    }
    
    public Onchange(final String action) {
        super(new FrontContent(action));
    }

    public Onchange(final FrontContent content) {
        super(content);
    }

    public Onchange(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
