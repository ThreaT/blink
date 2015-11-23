package cool.blink.front.javascript.template;

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
