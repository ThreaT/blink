package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.TopValue;

public class Top extends Property {

    public Top(Integer amount, TopValue topValue) {
        super(new FrontContent(amount + topValue.toString()));
    }

}
