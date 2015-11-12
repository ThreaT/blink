package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.BorderStyleValue;
import cool.blink.front.html.property.value.BorderWidthValue;
import cool.blink.front.html.property.value.ColorNameValue;

public class BorderRight extends Property {

    public BorderRight(Integer amount, BorderWidthValue borderWidthValue, BorderStyleValue borderStyles, ColorNameValue colorNames) {
        super(new FrontContent("border-right"), new FrontContent(amount.toString() + borderWidthValue.toString() + " " + colorNames.toString() + " " + borderStyles.toString()));
    }

    public BorderRight(Integer amount, BorderWidthValue borderWidthValue, BorderStyleValue borderStyles, Integer red, Integer green, Integer blue, Double alpha) {
        super(new FrontContent("border-right"), new FrontContent(amount.toString() + borderWidthValue.toString() + " " + borderStyles.toString() + " " + "rgba(" + red + ", " + green + ", " + blue + ", " + alpha + ")"));
    }

}
