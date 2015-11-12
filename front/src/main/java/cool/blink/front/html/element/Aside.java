package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Aside extends Element {

    public Aside() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Aside(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Aside(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
