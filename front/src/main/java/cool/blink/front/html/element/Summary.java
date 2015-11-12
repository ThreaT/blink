package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Summary extends Element {

    public Summary() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Summary(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Summary(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
