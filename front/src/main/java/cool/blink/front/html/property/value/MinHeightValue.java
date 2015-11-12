package cool.blink.front.html.property.value;

public enum MinHeightValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String minHeightValue;

    private MinHeightValue(final String minHeightValue) {
        this.minHeightValue = minHeightValue;
    }

    @Override
    public final String toString() {
        return minHeightValue;
    }

}
