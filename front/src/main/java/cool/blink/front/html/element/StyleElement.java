package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class StyleElement extends Element {

    public StyleElement() {
        super(TagType.fertile, "style", new FrontContent(""), new FrontContent(""));
    }

    public StyleElement(final FrontContent content) {
        super(TagType.fertile, "style", new FrontContent(""), content);
    }

    public StyleElement(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, "style", attributes, content);
    }

}
