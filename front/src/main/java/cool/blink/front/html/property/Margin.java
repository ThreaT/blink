package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.MarginBottomValue;
import cool.blink.front.html.property.value.MarginLeftValue;
import cool.blink.front.html.property.value.MarginRightValue;
import cool.blink.front.html.property.value.MarginTopValue;
import cool.blink.front.html.property.value.MarginValue;

public class Margin extends Property {

    public Margin(Integer amount, MarginValue marginValue) {
        super(new FrontContent(amount.toString() + marginValue.toString()));
    }

    public Margin(Integer topAmount, MarginTopValue marginTopValue, Integer rightAmount, MarginRightValue marginRightValue, Integer bottomAmount, MarginBottomValue marginBottomValue, Integer leftAmount, MarginLeftValue marginLeftValue) {
        super(new FrontContent(topAmount.toString() + marginTopValue.toString() + ", " + rightAmount.toString() + marginRightValue.toString() + ", " + bottomAmount.toString() + marginBottomValue + ", " + leftAmount.toString() + marginLeftValue.toString()));
    }

}
