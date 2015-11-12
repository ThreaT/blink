package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Oninput extends Attribute {

    public Oninput() {
        super(new FrontContent(""));
    }

    public Oninput(final FrontContent content) {
        super(content);
    }
    
    public Oninput(final String content) {
        super(new FrontContent(content));
    }

    public Oninput(final Property... property) {
        super(new FrontContent(generatePropertyValues(property)));
    }
}
