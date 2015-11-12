package cool.blink.front.html.property.value;

public enum FontValue {

    caption("caption"),
    icon("icon"),
    menu("menu"),
    message_box("message-box"),
    small_caption("small-caption"),
    status_bar("status-bar"),
    initial("initial"),
    inherit("inherit");

    private final String fontValue;

    private FontValue(final String fontValue) {
        this.fontValue = fontValue;
    }

    @Override
    public final String toString() {
        return fontValue;
    }

}
