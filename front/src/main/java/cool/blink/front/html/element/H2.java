package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class H2 extends Element {

    public H2() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public H2(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public H2(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
