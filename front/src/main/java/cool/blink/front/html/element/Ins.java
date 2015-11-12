package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Ins extends Element {

    public Ins() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Ins(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Ins(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
