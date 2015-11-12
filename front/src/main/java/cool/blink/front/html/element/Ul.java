package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Ul extends Element {

    public Ul() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Ul(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Ul(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
