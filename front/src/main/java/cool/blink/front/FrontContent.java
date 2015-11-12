package cool.blink.front;

import cool.blink.front.utilities.Longs;
import cool.blink.front.utilities.Token;

public class FrontContent {

    private final Long id;
    private final Token token;
    private final String published;
    private final String unpublished;

    public FrontContent(final String published) {
        this.id = Longs.generateUniqueId();
        this.token = Token.content;
        this.published = published;
        this.unpublished = this.token.toString() + this.id + this.token.toString() + published + this.token.toString() + this.id + this.token.toString();
    }

    public FrontContent(final String published, final String unpublished) {
        this.id = Longs.generateUniqueId();
        this.token = Token.content;
        this.published = published;
        this.unpublished = this.token.toString() + this.id + this.token.toString() + unpublished + this.token.toString() + this.id + this.token.toString();
    }

    public FrontContent(final Long id, final String published, final String unpublished) {
        this.id = id;
        this.token = Token.content;
        this.published = published;
        this.unpublished = this.token.toString() + this.id + this.token.toString() + unpublished + this.token.toString() + this.id + this.token.toString();
    }

    public final Long getId() {
        return id;
    }

    public final Token getToken() {
        return token;
    }

    public final String getPublished() {
        return published;
    }

    public final String getUnpublished() {
        return unpublished;
    }

    public interface Api {

        public String print();
    }

    @Override
    public String toString() {
        return "Content{" + "id=" + id + ", token=" + token + ", published=" + published + ", unpublished=" + unpublished + '}';
    }

}
