package cool.blink.front.html.property.value;

public enum HeightValue {

    blank(""),
    pixels("px"),
    points("pt"),
    centimeters("cm"),
    percent("%"),
    initial("initial"),
    inherit("inherit");

    private final String heightValue;

    private HeightValue(final String heightValue) {
        this.heightValue = heightValue;
    }

    @Override
    public final String toString() {
        return heightValue;
    }

}
