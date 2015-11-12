package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;

public class Value extends Attribute {

    public Value(final String property) {
        super(new FrontContent(property));
    }

}
