package cool.blink.front.html.element;

import cool.blink.front.html.Element;
import cool.blink.front.FrontContent;
import cool.blink.front.utilities.Elements.TagType;

public final class Br extends Element {

    public Br() {
        super(TagType.infertile, new FrontContent(""), new FrontContent(""));
    }

}
