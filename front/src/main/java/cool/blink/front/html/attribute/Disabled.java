package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;

public class Disabled extends Attribute {

    public Disabled() {
        super(new FrontContent(""));
    }

    public Disabled(final Boolean disabled) {
        super(new FrontContent(disabled.toString()));
    }
}
