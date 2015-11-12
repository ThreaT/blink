package cool.blink.front.html.property.value;

public enum TextDecorationValue {

    none("none"),
    underline("underline"),
    overline("overline"),
    line_through("line-through"),
    initial("initial"),
    inherit("inherit");

    private final String textDecorationValue;

    private TextDecorationValue(final String textDecorationValue) {
        this.textDecorationValue = textDecorationValue;
    }

    @Override
    public final String toString() {
        return textDecorationValue;
    }

}
