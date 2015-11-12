package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Del extends Element {

    public Del() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Del(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Del(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
