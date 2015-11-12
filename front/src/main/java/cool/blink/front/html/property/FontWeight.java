package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;

public class FontWeight extends Property {

    public FontWeight(Integer amount) {
        super(new FrontContent("font-weight"), new FrontContent(amount.toString()));
    }

}
