package cool.blink.front.html.property.value;

public enum DoctypeValue {

    HTML5("html"),
    HTML4_01_Strict("HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\""),
    HTML4_01_Transitional("HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\""),
    HTML4_01_Frameset("HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\""),
    XHTML1_0_Strict("html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\""),
    XHTML1_0_Transitional("html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\""),
    XHTML1_0_Frameset("html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\""),
    XHTML1_1("html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\"");

    private final String widthValue;

    private DoctypeValue(final String widthValue) {
        this.widthValue = widthValue;
    }

    @Override
    public final String toString() {
        return widthValue;
    }

}
