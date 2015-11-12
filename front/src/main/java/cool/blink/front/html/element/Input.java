package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Input extends Element {

    public Input() {
        super(TagType.infertile, new FrontContent(""), new FrontContent(""));
    }

    public Input(final FrontContent content) {
        super(TagType.infertile, new FrontContent(""), content);
    }

    public Input(final FrontContent attributes, final FrontContent content) {
        super(TagType.infertile, attributes, content);
    }

}
