package cool.blink.front.html.property.value;

public enum MarginTopValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    auto("auto"),
    initial("initial"),
    inherit("inherit");

    private final String marginTopValue;

    private MarginTopValue(final String marginTopValue) {
        this.marginTopValue = marginTopValue;
    }

    @Override
    public final String toString() {
        return marginTopValue;
    }

}
