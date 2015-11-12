package cool.blink.front.html.property.value;

public enum MarginRightValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    auto("auto"),
    initial("initial"),
    inherit("inherit");

    private final String marginRightValue;

    private MarginRightValue(final String marginRightValue) {
        this.marginRightValue = marginRightValue;
    }

    @Override
    public final String toString() {
        return marginRightValue;
    }

}
