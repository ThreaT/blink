package cool.blink.front.html.property.value;

public enum TopValue {

    auto("auto"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String topValue;

    private TopValue(final String topValue) {
        this.topValue = topValue;
    }

    @Override
    public final String toString() {
        return topValue;
    }

}
