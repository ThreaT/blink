package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Title extends Element {

    public Title() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Title(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Title(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
