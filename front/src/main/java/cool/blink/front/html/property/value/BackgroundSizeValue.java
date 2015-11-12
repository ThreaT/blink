package cool.blink.front.html.property.value;

public enum BackgroundSizeValue {

    auto("auto"),
    percent("%"),
    pixels("px"),
    cover("cover"),
    contain("contain"),
    initial("initial"),
    inherit("inherit");

    private final String backgroundSize;

    private BackgroundSizeValue(final String backgroundSize) {
        this.backgroundSize = backgroundSize;
    }

    @Override
    public final String toString() {
        return backgroundSize;
    }

}
