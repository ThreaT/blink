package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.Property;
import static cool.blink.front.utilities.Properties.generatePropertyValues;

public class Content extends Attribute {

    public Content() {
        super(new FrontContent(""));
    }

    public Content(final FrontContent properties) {
        super(properties);
    }

    public Content(final String properties) {
        super(new FrontContent(properties));
    }

    public Content(final Property... properties) {
        super(new FrontContent(generatePropertyValues(properties)));
    }

}
