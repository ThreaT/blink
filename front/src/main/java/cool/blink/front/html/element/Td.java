package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Td extends Element {

    public Td() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Td(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Td(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
