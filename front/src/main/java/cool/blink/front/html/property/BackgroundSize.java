package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BackgroundSizeValue;

public class BackgroundSize extends Property {

    public BackgroundSize(BackgroundSizeValue backgroundSizeValue) {
        super(new FrontContent("background-size"), new FrontContent(backgroundSizeValue.toString()));
    }

    public BackgroundSize(Integer amount, BackgroundSizeValue backgroundSizeValue) {
        super(new FrontContent("background-size"), new FrontContent(amount + backgroundSizeValue.toString()));
    }

}
