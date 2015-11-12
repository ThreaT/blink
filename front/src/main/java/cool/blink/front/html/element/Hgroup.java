package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Hgroup extends Element {

    public Hgroup() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Hgroup(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Hgroup(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
