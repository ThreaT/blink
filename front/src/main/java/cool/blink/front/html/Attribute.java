package cool.blink.front.html;

import cool.blink.front.FrontContent;

public abstract class Attribute implements FrontContent.Api {
    
    private final String key;
    private final FrontContent properties;
    private final FrontContent attribute;
    
    public Attribute(final FrontContent properties) {
        this.key = this.getClass().getSimpleName().toLowerCase();
        this.properties = properties;
        this.attribute = new FrontContent(" " + this.key + "=" + "\"" + this.properties.getPublished() + "\"");
    }
    
    public Attribute(final String key, final FrontContent properties) {
        this.key = key;
        this.properties = properties;
        this.attribute = new FrontContent(" " + this.key + "=" + "\"" + this.properties.getPublished() + "\"");
    }
    
    public final FrontContent getAttribute() {
        return attribute;
    }
    
    @Override
    public final String print() {
        return this.getAttribute().getPublished();
    }
    
}
