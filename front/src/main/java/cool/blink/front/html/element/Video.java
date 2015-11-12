package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Video extends Element {

    public Video() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Video(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Video(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
