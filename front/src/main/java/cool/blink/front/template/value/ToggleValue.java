package cool.blink.front.template.value;

public enum ToggleValue {

    visibility("visibility"),
    display("display");

    private final String toggleType;

    private ToggleValue(String toggleType) {
        this.toggleType = toggleType;
    }

    @Override
    public String toString() {
        return toggleType;
    }

}
