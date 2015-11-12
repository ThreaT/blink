package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Small extends Element {

    public Small() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Small(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Small(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
