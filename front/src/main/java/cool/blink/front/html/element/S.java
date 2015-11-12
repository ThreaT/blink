package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class S extends Element {

    public S() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public S(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public S(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
