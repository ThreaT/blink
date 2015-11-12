package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BackgroundRepeatValue;

public class BackgroundRepeat extends Property {

    public BackgroundRepeat(BackgroundRepeatValue backgroundRepeatValue) {
        super(new FrontContent("background-repeat"), new FrontContent(backgroundRepeatValue.toString()));
    }

}
