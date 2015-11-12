package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Q extends Element {

    public Q() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Q(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Q(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
