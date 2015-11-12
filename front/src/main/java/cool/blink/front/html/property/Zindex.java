package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.ZindexValue;

public class Zindex extends Property {

    public Zindex(Integer amount) {
        super(new FrontContent("z-index"), new FrontContent(amount.toString()));
    }

    public Zindex(ZindexValue zindexValue) {
        super(new FrontContent("z-index"), new FrontContent(zindexValue.toString()));
    }

}
