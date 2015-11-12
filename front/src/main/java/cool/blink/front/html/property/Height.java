package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.HeightValue;

public class Height extends Property {

    public Height(Integer amount, HeightValue heightValue) {
        super(new FrontContent(amount.toString() + heightValue.toString()));
    }

}
