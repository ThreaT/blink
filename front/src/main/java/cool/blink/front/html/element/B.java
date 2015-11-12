package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class B extends Element {

    public B() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public B(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public B(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
