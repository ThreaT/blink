package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Ruby extends Element {

    public Ruby() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Ruby(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Ruby(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
