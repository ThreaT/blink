package cool.blink.front.html.property.value;

public enum MaxHeightValue {

    blank(""),
    none("none"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String maxHeightValue;

    private MaxHeightValue(final String maxHeightValue) {
        this.maxHeightValue = maxHeightValue;
    }

    @Override
    public final String toString() {
        return maxHeightValue;
    }

}
