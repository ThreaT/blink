package cool.blink.front.html.property.value;

public enum WidthValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String widthValue;

    private WidthValue(final String widthValue) {
        this.widthValue = widthValue;
    }

    @Override
    public final String toString() {
        return widthValue;
    }

}
