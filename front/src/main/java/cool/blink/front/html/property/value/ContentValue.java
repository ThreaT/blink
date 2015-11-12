package cool.blink.front.html.property.value;

public enum ContentValue {

    blank("\"\""),
    normal("normal"),
    none("none"),
    counter("counter"),
    open_quote("open-quote"),
    close_quote("close-quote"),
    no_open_quote("no-open-quote"),
    no_close_quote("no-close-quote"),
    initial("initial"),
    inherit("inherit");

    private final String contentValue;

    private ContentValue(final String contentValue) {
        this.contentValue = contentValue;
    }

    @Override
    public final String toString() {
        return contentValue;
    }

}
