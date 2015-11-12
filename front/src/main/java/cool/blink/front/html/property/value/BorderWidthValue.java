package cool.blink.front.html.property.value;

public enum BorderWidthValue {

    blank(""),
    medium("medium"),
    thin("thin"),
    thick("thick"),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    initial("initial"),
    inherit("inherit");

    private final String borderWidthValue;

    private BorderWidthValue(final String borderWidthValue) {
        this.borderWidthValue = borderWidthValue;
    }

    @Override
    public final String toString() {
        return borderWidthValue;
    }

}
