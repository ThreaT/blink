package cool.blink.front.html.property.value;

public enum MarginLeftValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    auto("auto"),
    initial("initial"),
    inherit("inherit");

    private final String marginLeftValue;

    private MarginLeftValue(final String marginLeftValue) {
        this.marginLeftValue = marginLeftValue;
    }

    @Override
    public final String toString() {
        return marginLeftValue;
    }

}
