package cool.blink.front.html.property.value;

public enum FontSizeValue {

    blank(""),
    medium("medium"),
    xx_small("xx-small"),
    x_small("x-small"),
    small("small"),
    large("large"),
    x_large("x-large"),
    xx_large("xx-large"),
    smaller("smaller"),
    larger("larger"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String fontSizeValue;

    private FontSizeValue(final String fontSizeValue) {
        this.fontSizeValue = fontSizeValue;
    }

    @Override
    public final String toString() {
        return fontSizeValue;
    }

}
