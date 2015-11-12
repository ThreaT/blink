package cool.blink.front.html.property.value;

public enum BackgroundColorValue {

    transparent("transparent"),
    initial("initial"),
    inherit("inherit");

    private final String backgroundColor;

    private BackgroundColorValue(final String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public final String toString() {
        return backgroundColor;
    }

}
