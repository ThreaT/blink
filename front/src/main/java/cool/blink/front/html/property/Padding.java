package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.PaddingBottomValue;
import cool.blink.front.html.property.value.PaddingLeftValue;
import cool.blink.front.html.property.value.PaddingRightValue;
import cool.blink.front.html.property.value.PaddingTopValue;
import cool.blink.front.html.property.value.PaddingValue;

public class Padding extends Property {

    public Padding(Integer amount, PaddingValue paddingValue) {
        super(new FrontContent(amount.toString() + paddingValue.toString()));
    }

    public Padding(Integer topAmount, PaddingTopValue paddingTopValue, Integer rightAmount, PaddingRightValue paddingRightValue, Integer bottomAmount, PaddingBottomValue paddingBottomValue, Integer leftAmount, PaddingLeftValue paddingLeftValue) {
        super(new FrontContent(topAmount.toString() + paddingTopValue.toString() + ", " + rightAmount.toString() + paddingRightValue.toString() + ", " + bottomAmount.toString() + paddingBottomValue + ", " + leftAmount.toString() + paddingLeftValue.toString()));
    }

}
