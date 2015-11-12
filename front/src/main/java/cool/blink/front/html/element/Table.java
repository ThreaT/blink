package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Table extends Element {

    public Table() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Table(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Table(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
