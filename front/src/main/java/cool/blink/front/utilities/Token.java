package cool.blink.front.utilities;

public enum Token {

    content("###front#token###"),
    contentRegularExpression("([#]{3}(front){1}[#]{1}(token){1}[#]{3}[\\d]{9}[#]{3}(front){1}[#]{1}(token){1}[#]{3})");

    private final String token;

    private Token(final String token) {
        this.token = token;
    }

    @Override
    public final String toString() {
        return token;
    }

}
