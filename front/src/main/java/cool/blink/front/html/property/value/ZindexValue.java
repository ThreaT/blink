package cool.blink.front.html.property.value;

public enum ZindexValue {

    auto("auto"),
    initial("initial"),
    inherit("inherit");

    private final String zindexValue;

    private ZindexValue(final String zindexValue) {
        this.zindexValue = zindexValue;
    }

    @Override
    public final String toString() {
        return zindexValue;
    }

}
