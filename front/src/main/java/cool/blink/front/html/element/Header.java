package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Header extends Element {

    public Header() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Header(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Header(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
