package cool.blink.front.html.property.value;

public enum LineHeightValue {

    normal("normal"),
    number("number"),
    length("length"),
    percentage("%"),
    initial("initial"),
    inherit("inherit");

    private final String lineHeightValue;

    private LineHeightValue(final String lineHeightValue) {
        this.lineHeightValue = lineHeightValue;
    }

    @Override
    public final String toString() {
        return lineHeightValue;
    }

}
