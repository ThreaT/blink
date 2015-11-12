package cool.blink.front.html.property.value;

public enum ButtonTypeValue {

    button("button"),
    submit("submit"),
    reset("reset");

    private final String buttonTypeValue;

    private ButtonTypeValue(final String buttonTypeValue) {
        this.buttonTypeValue = buttonTypeValue;
    }

    @Override
    public final String toString() {
        return buttonTypeValue;
    }

}
