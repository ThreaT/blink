package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.property.value.ButtonTypeValue;

public class ButtonType extends Attribute {

    public ButtonType(final ButtonTypeValue buttonTypeValue) {
        super("type", new FrontContent(buttonTypeValue.toString()));
    }

}
