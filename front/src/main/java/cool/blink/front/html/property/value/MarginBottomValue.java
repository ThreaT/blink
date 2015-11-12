package cool.blink.front.html.property.value;

public enum MarginBottomValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    auto("auto"),
    initial("initial"),
    inherit("inherit");

    private final String marginBottomValue;

    private MarginBottomValue(final String marginBottomValue) {
        this.marginBottomValue = marginBottomValue;
    }

    @Override
    public final String toString() {
        return marginBottomValue;
    }

}
