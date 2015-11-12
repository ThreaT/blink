package cool.blink.front.html.property.value;

public enum BorderSpacingValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    initial("initial"),
    inherit("inherit");

    private final String borderSpacingValue;

    private BorderSpacingValue(final String borderSpacingValue) {
        this.borderSpacingValue = borderSpacingValue;
    }

    @Override
    public final String toString() {
        return borderSpacingValue;
    }

}
