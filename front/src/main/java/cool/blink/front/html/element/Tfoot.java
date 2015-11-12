package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Tfoot extends Element {

    public Tfoot() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Tfoot(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Tfoot(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
