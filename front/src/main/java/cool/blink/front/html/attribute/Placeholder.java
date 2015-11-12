package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;

public class Placeholder extends Attribute {

    public Placeholder() {
        super(new FrontContent(""));
    }

    public Placeholder(final String placeholder) {
        super(new FrontContent(placeholder));
    }
}
