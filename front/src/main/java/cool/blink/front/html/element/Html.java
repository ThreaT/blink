package cool.blink.front.html.element;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Element;
import cool.blink.front.utilities.Elements.TagType;

public final class Html extends Element {

    public Html() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Html(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Html(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
