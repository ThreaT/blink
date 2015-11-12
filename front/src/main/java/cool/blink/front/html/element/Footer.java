package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Footer extends Element {

    public Footer() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Footer(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Footer(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
