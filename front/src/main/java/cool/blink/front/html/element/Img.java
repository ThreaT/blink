package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Img extends Element {

    public Img() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Img(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Img(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
