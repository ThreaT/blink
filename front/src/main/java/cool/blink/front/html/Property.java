package cool.blink.front.html;

import cool.blink.front.FrontContent;

public abstract class Property implements FrontContent.Api {

    private final FrontContent key;
    private final FrontContent value;
    private final FrontContent property;

    public Property(final FrontContent value) {
        this.key = new FrontContent(this.getClass().getSimpleName().toLowerCase());
        this.value = value;
        this.property = new FrontContent(key.getPublished() + ":" + value.getPublished() + ";");
    }

    public Property(final FrontContent key, final FrontContent value) {
        this.key = key;
        this.value = value;
        this.property = new FrontContent(key.getPublished() + ":" + value.getPublished() + ";");
    }

    public final FrontContent getKey() {
        return key;
    }

    public final FrontContent getValue() {
        return value;
    }

    public final FrontContent getProperty() {
        return property;
    }

    @Override
    public final String print() {
        return this.getProperty().getPublished();
    }

    @Override
    public final String toString() {
        return "Property{" + "key=" + key + ", value=" + value + ", property=" + property + '}';
    }

}
