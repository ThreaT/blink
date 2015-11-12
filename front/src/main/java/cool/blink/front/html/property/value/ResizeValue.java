package cool.blink.front.html.property.value;

public enum ResizeValue {

    none("none"),
    both("both"),
    horizontal("horizontal"),
    vertical("vertical"),
    initial("initial"),
    inherit("inherit");

    private final String resizeValue;

    private ResizeValue(final String resizeValue) {
        this.resizeValue = resizeValue;
    }

    @Override
    public final String toString() {
        return resizeValue;
    }

}
