package cool.blink.front.html.property.value;

public enum QuoteValue {

    none("none"),
    string("string"),
    initial("initial"),
    inherit("inherit");

    private final String quote;

    private QuoteValue(final String quote) {
        this.quote = quote;
    }

    @Override
    public final String toString() {
        return quote;
    }

}
