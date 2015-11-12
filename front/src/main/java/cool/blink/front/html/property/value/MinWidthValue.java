package cool.blink.front.html.property.value;

public enum MinWidthValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String minWidthValue;

    private MinWidthValue(final String minWidthValue) {
        this.minWidthValue = minWidthValue;
    }

    @Override
    public final String toString() {
        return minWidthValue;
    }

}
