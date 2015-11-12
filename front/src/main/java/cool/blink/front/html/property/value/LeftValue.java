package cool.blink.front.html.property.value;

public enum LeftValue {

    auto("auto"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String leftValue;

    private LeftValue(final String leftValue) {
        this.leftValue = leftValue;
    }

    @Override
    public final String toString() {
        return leftValue;
    }

}
