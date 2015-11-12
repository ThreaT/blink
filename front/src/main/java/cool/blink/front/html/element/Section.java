package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Section extends Element {

    public Section() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Section(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Section(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
