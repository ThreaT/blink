package cool.blink.front.html.property.value;

public enum BackgroundImageValue {

    url("url"),
    none("none"),
    initial("initial"),
    inherit("inherit");

    private final String backgroundImage;

    private BackgroundImageValue(final String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public final String toString() {
        return backgroundImage;
    }

}
