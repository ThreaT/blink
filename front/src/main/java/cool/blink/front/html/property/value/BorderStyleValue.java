package cool.blink.front.html.property.value;

public enum BorderStyleValue {

    dotted("dotted"),
    dashed("dashed"),
    solid("solid"),
    double_("double_"),
    groove("groove"),
    ridge("ridge"),
    inset("inset"),
    outset("outset");

    private final String borderStyle;

    private BorderStyleValue(final String borderStyle) {
        this.borderStyle = borderStyle;
    }

    @Override
    public final String toString() {
        return borderStyle;
    }

}
