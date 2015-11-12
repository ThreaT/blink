package cool.blink.front.html.property.value;

public enum BorderCollapseValue {

    separate("separate"),
    collapse("collapse"),
    initial("initial"),
    inherit("inherit");

    private final String borderCollapseValue;

    private BorderCollapseValue(final String borderCollapseValue) {
        this.borderCollapseValue = borderCollapseValue;
    }

    @Override
    public final String toString() {
        return borderCollapseValue;
    }

}
