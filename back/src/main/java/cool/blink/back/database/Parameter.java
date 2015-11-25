package cool.blink.back.database;

public final class Parameter {

    private final Integer placeholderIndex;
    private final Object value;
    private final Class type;

    public Parameter(final Integer placeholderIndex, final Object value, final Class type) {
        this.placeholderIndex = placeholderIndex;
        this.value = value;
        this.type = type;
    }

    public final Integer getPlaceholderIndex() {
        return placeholderIndex;
    }

    public final Object getValue() {
        return value;
    }

    public final Class getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Parameter{" + "placeholderIndex=" + placeholderIndex + ", value=" + value + ", type=" + type + '}';
    }

}
