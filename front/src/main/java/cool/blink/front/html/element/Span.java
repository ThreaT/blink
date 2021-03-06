package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Span extends Element {

    public Span() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Span(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Span(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
