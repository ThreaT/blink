package cool.blink.front.html.property.value;

public enum MethodValue {

    GET("GET"),
    HEAD("HEAD"),
    CHECKOUT("CHECKOUT"),
    SHOWMETHOD("SHOWMETHOD"),
    PUT("PUT"),
    DELETE("DELETE"),
    POST("POST"),
    LINK("LINK"),
    UNLINK("UNLINK"),
    CHECKIN("CHECKIN"),
    TEXTSEARCH("TEXTSEARCH"),
    SPACEJUMP("SPACEJUMP"),
    SEARCH("SEARCH");

    private final String methodValue;

    private MethodValue(String methodValue) {
        this.methodValue = methodValue;
    }

    @Override
    public String toString() {
        return methodValue;
    }
}
