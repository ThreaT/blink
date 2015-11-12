package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Strong extends Element {

    public Strong() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Strong(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Strong(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
