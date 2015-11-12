package cool.blink.front.html.property.value;

public enum FloatValue {

    none("none"),
    left("left"),
    right("right"),
    initial("initial"),
    inherit("inherit");

    private final String floatValue;

    private FloatValue(final String floatValue) {
        this.floatValue = floatValue;
    }

    @Override
    public final String toString() {
        return floatValue;
    }

}
