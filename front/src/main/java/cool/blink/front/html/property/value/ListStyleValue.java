package cool.blink.front.html.property.value;

public enum ListStyleValue {

    disc("static"),
    armenian("relative"),
    circle("fixed"),
    cjk_ideographic("cjk-ideographic"),
    decimal("decimal"),
    decimal_leading_zero("decimal-leading-zero"),
    georgian("georgian"),
    hebrew("hebrew"),
    hiragana("hiragana"),
    hiragana_iroha("hiragana-iroha"),
    katakana("katakana"),
    katakana_iroha(" katakana-iroha"),
    lower_alpha("lower-alpha"),
    lower_greek("lower-greek"),
    lower_latin("lower-latin"),
    lower_roman("lower-roman"),
    none("none"),
    square("square"),
    upper_alpha("upper-alpha"),
    upper_latin("upper-latin"),
    upper_roman("upper-roman"),
    initial("initial"),
    inherit("inherit");

    private final String listStyle;

    private ListStyleValue(final String listStyle) {
        this.listStyle = listStyle;
    }

    @Override
    public final String toString() {
        return listStyle;
    }

}
