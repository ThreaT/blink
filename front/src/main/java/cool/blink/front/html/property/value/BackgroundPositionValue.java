package cool.blink.front.html.property.value;

public enum BackgroundPositionValue {

    left_top("left top"),
    left_center("left center"),
    left_bottom("left bottom"),
    right_top("right top"),
    right_center("right center"),
    right_bottom("right bottom"),
    center_top("center top"),
    center_center("center center"),
    center_bottom("center bottom"),
    percent("percent"),
    initial("initial"),
    inherit("inherit");

    private final String backgroundPositionValue;

    private BackgroundPositionValue(final String backgroundPositionValue) {
        this.backgroundPositionValue = backgroundPositionValue;
    }

    @Override
    public final String toString() {
        return backgroundPositionValue;
    }

}
