package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Meta extends Element {

    public Meta() {
        super(TagType.metaHybrid, new FrontContent(""), new FrontContent(""));
    }

    public Meta(final FrontContent content) {
        super(TagType.metaHybrid, new FrontContent(""), content);
    }

    public Meta(final FrontContent attributes, final FrontContent content) {
        super(TagType.metaHybrid, attributes, content);
    }

}
