package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Tt extends Element {

    public Tt() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Tt(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Tt(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
