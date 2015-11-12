package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class U extends Element {

    public U() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public U(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public U(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
