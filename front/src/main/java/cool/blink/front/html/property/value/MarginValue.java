package cool.blink.front.html.property.value;

public enum MarginValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String marginValue;

    private MarginValue(final String marginValue) {
        this.marginValue = marginValue;
    }

    @Override
    public final String toString() {
        return marginValue;
    }

}
