package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.ContentValue;

public class Content extends Property {

    public Content(ContentValue contentValue) {
        super(new FrontContent(contentValue.toString()));
    }

}
