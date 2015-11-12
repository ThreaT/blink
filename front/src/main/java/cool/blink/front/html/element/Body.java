package cool.blink.front.html.element;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Element;
import cool.blink.front.utilities.Elements.TagType;

public final class Body extends Element {

    public Body() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Body(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Body(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
