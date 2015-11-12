package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Strike extends Element {

    public Strike() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Strike(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Strike(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
