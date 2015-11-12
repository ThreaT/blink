package cool.blink.front.html.property.value;

public enum ScriptTypeValue {

    media_type("media_type");

    private final String scriptTypeValue;

    private ScriptTypeValue(final String scriptTypeValue) {
        this.scriptTypeValue = scriptTypeValue;
    }

    @Override
    public final String toString() {
        return scriptTypeValue;
    }

}
