package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Figure extends Element {

    public Figure() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Figure(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Figure(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
