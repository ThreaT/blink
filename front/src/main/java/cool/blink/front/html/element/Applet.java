package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Applet extends Element {

    public Applet() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Applet(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Applet(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
