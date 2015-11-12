package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Sub extends Element {

    public Sub() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Sub(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Sub(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
