package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;

public class Src extends Attribute {

    public Src() {
        super(new FrontContent(""));
    }

    public Src(final String src) {
        super(new FrontContent(src));
    }
}
