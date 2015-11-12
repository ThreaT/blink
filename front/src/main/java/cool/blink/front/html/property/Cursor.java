package cool.blink.front.html.property;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Property;
import cool.blink.front.html.property.value.CursorValue;

public class Cursor extends Property {

    public Cursor(CursorValue cursor) {
        super(new FrontContent(cursor.toString()));
    }

}
