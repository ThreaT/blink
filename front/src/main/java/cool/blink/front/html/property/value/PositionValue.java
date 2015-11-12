package cool.blink.front.html.property.value;

public enum PositionValue {

    static_("static"),
    relative("relative"),
    fixed("fixed"),
    absolute("absolute");

    private final String positionValue;

    private PositionValue(final String positionValue) {
        this.positionValue = positionValue;
    }

    @Override
    public final String toString() {
        return positionValue;
    }

}
