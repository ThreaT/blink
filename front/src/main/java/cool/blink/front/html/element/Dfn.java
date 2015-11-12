package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Dfn extends Element {

    public Dfn() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Dfn(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Dfn(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
