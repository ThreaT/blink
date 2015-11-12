package cool.blink.front.html.property.value;

public enum HttpEquivValue {

    content_type("content-type"),
    default_style("default-style"),
    refresh("refresh");

    private final String httpEquivValue;

    private HttpEquivValue(final String httpEquivValue) {
        this.httpEquivValue = httpEquivValue;
    }

    @Override
    public final String toString() {
        return httpEquivValue;
    }

}
