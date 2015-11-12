package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Abbr extends Element {

    public Abbr() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Abbr(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Abbr(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
