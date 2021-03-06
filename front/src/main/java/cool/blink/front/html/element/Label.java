package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Label extends Element {

    public Label() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Label(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Label(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
