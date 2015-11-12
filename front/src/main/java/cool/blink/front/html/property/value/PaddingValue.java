package cool.blink.front.html.property.value;

public enum PaddingValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String paddingValue;

    private PaddingValue(final String paddingValue) {
        this.paddingValue = paddingValue;
    }

    @Override
    public final String toString() {
        return paddingValue;
    }

}
