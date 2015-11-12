package cool.blink.front.html;

import cool.blink.front.FrontContent;

public class Text implements FrontContent.Api {

    private final FrontContent text;

    public Text(String text) {
        this.text = new FrontContent(text);
    }

    public Text(FrontContent text) {
        this.text = text;
    }

    public final FrontContent getText() {
        return text;
    }

    @Override
    public String print() {
        return this.text.getPublished();
    }
}
