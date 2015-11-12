package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;

public class Id extends Attribute {

    public Id() {
        super(new FrontContent(""));
    }

    public Id(final String id) {
        super(new FrontContent(id));
    }
}
