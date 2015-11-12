package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Samp extends Element {

    public Samp() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Samp(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Samp(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
