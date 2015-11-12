package cool.blink.front.html.property.value;

public enum FontFamilyValue {

    initial("initial"),
    inherit("inherit");

    private final String fontFamilyValue;

    private FontFamilyValue(final String fontFamilyValue) {
        this.fontFamilyValue = fontFamilyValue;
    }

    @Override
    public final String toString() {
        return fontFamilyValue;
    }

}
