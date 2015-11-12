package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Thead extends Element {

    public Thead() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Thead(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Thead(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
