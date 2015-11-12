package cool.blink.front.html.property.value;

public enum VerticalAlignValue {

    baseline("baseline"),
    length("length"),
    percent("%"),
    sub("sub"),
    super_("super"),
    top("top"),
    text_top("text-top"),
    middle("middle"),
    bottom("bottom"),
    text_bottom("text-bottom");

    private final String verticalAlign;

    private VerticalAlignValue(final String verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    @Override
    public final String toString() {
        return verticalAlign;
    }

}
