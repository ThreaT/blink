package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Audio extends Element {

    public Audio() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Audio(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Audio(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
