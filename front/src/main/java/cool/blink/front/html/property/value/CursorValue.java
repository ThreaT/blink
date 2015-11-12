package cool.blink.front.html.property.value;

public enum CursorValue {

    auto("px"),
    pointer("pointer");

    private final String cursor;

    private CursorValue(final String cursor) {
        this.cursor = cursor;
    }

    @Override
    public final String toString() {
        return cursor;
    }

}
