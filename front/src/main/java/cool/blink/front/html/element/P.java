package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class P extends Element {

    public P() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public P(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public P(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
