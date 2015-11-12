package cool.blink.front.html.property.value;

public enum PaddingLeftValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String paddingLeftValue;

    private PaddingLeftValue(final String paddingLeftValue) {
        this.paddingLeftValue = paddingLeftValue;
    }

    @Override
    public final String toString() {
        return paddingLeftValue;
    }

}
