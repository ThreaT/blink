package cool.blink.front.html.property.value;

public enum StyleTypeValue {

    media_type("media_type");

    private final String styleTypeValue;

    private StyleTypeValue(final String styleTypeValue) {
        this.styleTypeValue = styleTypeValue;
    }

    @Override
    public final String toString() {
        return styleTypeValue;
    }

}
