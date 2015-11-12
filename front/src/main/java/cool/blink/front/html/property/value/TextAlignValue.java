package cool.blink.front.html.property.value;

public enum TextAlignValue {

    left("left"),
    right("right"),
    center("center"),
    justify("justify"),
    initial("initial"),
    inherit("inherit");

    private final String textAlignValue;

    private TextAlignValue(final String textAlignValue) {
        this.textAlignValue = textAlignValue;
    }

    @Override
    public final String toString() {
        return textAlignValue;
    }

}
