package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Embed extends Element {

    public Embed() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Embed(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Embed(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
