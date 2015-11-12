package cool.blink.front.html.property.value;

public enum BottomValue {

    auto("auto"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String bottomValue;

    private BottomValue(final String bottomValue) {
        this.bottomValue = bottomValue;
    }

    @Override
    public final String toString() {
        return bottomValue;
    }

}
