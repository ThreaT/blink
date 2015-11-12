package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.property.value.InputTypeValue;

public class InputType extends Attribute {

    public InputType(InputTypeValue inputTypeValue) {
        super("type", new FrontContent(inputTypeValue.toString()));
    }

}
