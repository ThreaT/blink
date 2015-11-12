package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.property.value.HttpEquivValue;

public class HttpEquiv extends Attribute {

    public HttpEquiv(final HttpEquivValue httpEquivValue) {
        super(new FrontContent(httpEquivValue.toString()));
    }

}
