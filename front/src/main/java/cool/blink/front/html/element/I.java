package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class I extends Element {

    public I() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public I(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public I(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
