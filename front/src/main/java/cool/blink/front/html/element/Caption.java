package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Caption extends Element {

    public Caption() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Caption(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Caption(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
