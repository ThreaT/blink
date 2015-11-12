package cool.blink.front.utilities;

public class FrontContents {

    public static final synchronized String publish(String unpublished) {
        return unpublished.replaceAll(Token.contentRegularExpression.toString(), "");
    }
}
