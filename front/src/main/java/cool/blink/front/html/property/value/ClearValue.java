package cool.blink.front.html.property.value;

public enum ClearValue {

    none("none"),
    left("left"),
    right("right"),
    both("both"),
    initial("initial"),
    inherit("inherit");

    private final String clearValue;

    private ClearValue(final String clearValue) {
        this.clearValue = clearValue;
    }

    @Override
    public final String toString() {
        return clearValue;
    }

}
