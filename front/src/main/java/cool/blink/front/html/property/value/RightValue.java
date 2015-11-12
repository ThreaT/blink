package cool.blink.front.html.property.value;

public enum RightValue {

    auto("auto"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String rightValue;

    private RightValue(final String rightValue) {
        this.rightValue = rightValue;
    }

    @Override
    public final String toString() {
        return rightValue;
    }

}
