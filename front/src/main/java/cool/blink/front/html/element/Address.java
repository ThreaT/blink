package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Address extends Element {

    public Address() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Address(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Address(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
