package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Kbd extends Element {

    public Kbd() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Kbd(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Kbd(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
