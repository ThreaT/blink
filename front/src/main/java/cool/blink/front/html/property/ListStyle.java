package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.ListStyleValue;

public class ListStyle extends Property {

    public ListStyle(ListStyleValue listStyle) {
        super(new FrontContent("list-style"), new FrontContent(listStyle.toString()));
    }

}
