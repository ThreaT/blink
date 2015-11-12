package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Dt extends Element {

    public Dt() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Dt(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Dt(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
