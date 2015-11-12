package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BackgroundColorValue;
import cool.blink.front.html.property.value.BackgroundRepeatValue;

public class Background extends Property {

    public Background(BackgroundColorValue backgroundColorValue) {
        super(new FrontContent(backgroundColorValue.toString()));
    }

    public Background(String backgroundColor) {
        super(new FrontContent(backgroundColor));
    }

    public Background(String backgroundImage, BackgroundRepeatValue backgroundRepeats) {
        super(new FrontContent(backgroundImage + " " + backgroundRepeats.toString()));
    }

    public Background(String backgroundColor, String backgroundImage, BackgroundRepeatValue backgroundRepeats) {
        super(new FrontContent(backgroundColor + " " + backgroundImage + " " + backgroundRepeats.toString()));
    }

}
