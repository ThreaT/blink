package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.QuoteValue;

public class Quotes extends Property {

    public Quotes(QuoteValue quoteValue) {
        super(new FrontContent(quoteValue.toString()));
    }

}
