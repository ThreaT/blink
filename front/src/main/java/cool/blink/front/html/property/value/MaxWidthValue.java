package cool.blink.front.html.property.value;

public enum MaxWidthValue {

    blank(""),
    none("none"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String maxWidthValue;

    private MaxWidthValue(final String maxWidthValue) {
        this.maxWidthValue = maxWidthValue;
    }

    @Override
    public final String toString() {
        return maxWidthValue;
    }

}
