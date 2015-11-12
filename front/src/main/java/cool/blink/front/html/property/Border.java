package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BorderStyleValue;
import cool.blink.front.html.property.value.BorderWidthValue;
import cool.blink.front.html.property.value.ColorNameValue;

public class Border extends Property {

    public Border(Integer amount, BorderWidthValue borderWidthValue) {
        super(new FrontContent(amount.toString() + borderWidthValue.toString()));
    }

    public Border(Integer amount, BorderWidthValue borderWidthValue, ColorNameValue colorNames, BorderStyleValue borderStyles) {
        super(new FrontContent(amount.toString() + borderWidthValue.toString() + " " + colorNames.toString() + " " + borderStyles.toString()));
    }

}
