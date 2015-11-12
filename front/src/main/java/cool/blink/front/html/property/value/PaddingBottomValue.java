package cool.blink.front.html.property.value;

public enum PaddingBottomValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String paddingBottomValue;

    private PaddingBottomValue(final String paddingBottomValue) {
        this.paddingBottomValue = paddingBottomValue;
    }

    @Override
    public final String toString() {
        return paddingBottomValue;
    }

}
