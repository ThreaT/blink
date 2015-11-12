package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;

public class Name extends Attribute {

    public Name() {
        super(new FrontContent(""));
    }

    public Name(final String name) {
        super(new FrontContent(name));
    }
}
