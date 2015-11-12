package cool.blink.front.html.property.value;

public enum PaddingRightValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String paddingRightValue;

    private PaddingRightValue(final String paddingRightValue) {
        this.paddingRightValue = paddingRightValue;
    }

    @Override
    public final String toString() {
        return paddingRightValue;
    }

}
