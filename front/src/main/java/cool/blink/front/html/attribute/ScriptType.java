package cool.blink.front.html.attribute;

import cool.blink.front.FrontContent;
import cool.blink.front.html.Attribute;
import cool.blink.front.html.property.value.ScriptTypeValue;

public class ScriptType extends Attribute {

    public ScriptType(final ScriptTypeValue scriptTypeValue) {
        super("type", new FrontContent(scriptTypeValue.toString()));
    }

    public ScriptType(final String properties) {
        super("type", new FrontContent(properties));
    }

}
