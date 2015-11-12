package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Onload extends Attribute {

    public Onload() {
        super(new FrontContent(""));
    }
    
    public Onload(final String action) {
        super(new FrontContent(action));
    }

    public Onload(final FrontContent content) {
        super(content);
    }

    public Onload(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
