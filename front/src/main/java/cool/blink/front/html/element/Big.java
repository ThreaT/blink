package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Big extends Element {

    public Big() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Big(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Big(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
