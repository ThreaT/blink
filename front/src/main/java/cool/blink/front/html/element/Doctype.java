package cool.blink.front.html.element;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Element;
import cool.blink.front.html.property.value.DoctypeValue;
import cool.blink.front.utilities.Elements.TagType;

public final class Doctype extends Element {

    public Doctype(final DoctypeValue doctypeValue) {
        super(TagType.docTypeHybrid, Doctype.class.getSimpleName().toUpperCase(), new FrontContent(" " + doctypeValue.toString()), new FrontContent(""));
    }

}
