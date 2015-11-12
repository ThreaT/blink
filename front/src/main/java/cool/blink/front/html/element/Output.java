package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Output extends Element {

    public Output() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Output(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Output(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
