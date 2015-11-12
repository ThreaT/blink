package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Menu extends Element {

    public Menu() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Menu(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Menu(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
