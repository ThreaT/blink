package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Tr extends Element {

    public Tr() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Tr(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Tr(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
