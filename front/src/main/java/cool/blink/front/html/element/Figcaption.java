package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Figcaption extends Element {

    public Figcaption() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Figcaption(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Figcaption(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
