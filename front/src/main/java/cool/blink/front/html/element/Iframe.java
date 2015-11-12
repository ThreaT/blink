package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Iframe extends Element {

    public Iframe() {
        super(TagType.fertile, new FrontContent(""), new FrontContent(""));
    }

    public Iframe(final FrontContent content) {
        super(TagType.fertile, new FrontContent(""), content);
    }

    public Iframe(final FrontContent attributes, final FrontContent content) {
        super(TagType.fertile, attributes, content);
    }

}
