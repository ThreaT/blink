package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.PaddingBottomValue;

public class PaddingBottom extends Property {

    public PaddingBottom(Integer amount, PaddingBottomValue paddingBottomValue) {
        super(new FrontContent("padding-bottom"), new FrontContent(amount.toString() + paddingBottomValue.toString()));
    }

}
