package cool.blink.front.html.property.value;

public enum PaddingTopValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String paddingTopValue;

    private PaddingTopValue(final String paddingTopValue) {
        this.paddingTopValue = paddingTopValue;
    }

    @Override
    public final String toString() {
        return paddingTopValue;
    }

}
