package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Div extends Element {

    public Div() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Div(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Div(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
