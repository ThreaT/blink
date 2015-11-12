package cool.blink.front.html.property.value;

public enum BackgroundRepeatValue {

    repeat("repeat"),
    repeat_x("repeat-x"),
    repeat_y("repeat-y"),
    no_repeat("no-repeat"),
    initial("initial"),
    inherit("inherit");

    private final String backgroundRepeat;

    private BackgroundRepeatValue(final String backgroundRepeat) {
        this.backgroundRepeat = backgroundRepeat;
    }

    @Override
    public final String toString() {
        return backgroundRepeat;
    }

}
