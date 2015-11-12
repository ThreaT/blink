package cool.blink.front.html.property.value;

public enum VisibilityValue {

    visible("visible"),
    hidden("hidden"),
    collapse("collapse"),
    initial("initial"),
    inherit("inherit");

    private final String visibilityValue;

    private VisibilityValue(final String visibilityValue) {
        this.visibilityValue = visibilityValue;
    }

    @Override
    public final String toString() {
        return visibilityValue;
    }

}
