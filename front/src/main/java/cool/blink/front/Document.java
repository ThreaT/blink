package cool.blink.front;

import cool.blink.front.html.Element;
import cool.blink.front.html.Text;
import cool.blink.front.utilities.FrontContents;

public class Document implements FrontContent.Api {

    private final FrontContent document;

    public Document() {
        this.document = new FrontContent("");
    }

    public Document(final FrontContent document) {
        this.document = document;
    }

    public FrontContent getDocument() {
        return document;
    }

    public final Document prepend(final Text text) {
        String newUnpublished = text.getText().getUnpublished() + this.getDocument().getUnpublished();
        String newPublished = FrontContents.publish(newUnpublished);
        FrontContent newContent = new FrontContent(text.getText().getId(), newPublished, newUnpublished);
        return new Document(newContent);
    }

    public final Document append(final Text text) {
        String newUnpublished = this.getDocument().getUnpublished() + text.getText().getUnpublished();
        String newPublished = FrontContents.publish(newUnpublished);
        FrontContent newContent = new FrontContent(text.getText().getId(), newPublished, newUnpublished);
        return new Document(newContent);
    }

    public final Document removeFirst(final Text text) {
        throw new UnsupportedOperationException();
    }

    public final Document removeAll(final Text text) {
        String newUnpublished = this.getDocument().getUnpublished().replaceAll(text.getText().getUnpublished(), "");
        String newPublished = FrontContents.publish(newUnpublished);
        FrontContent newContent = new FrontContent(newPublished, newUnpublished);
        return new Document(newContent);
    }

    public final Document prepend(final Element element) {
        String newUnpublished = element.getElement().getUnpublished() + this.getDocument().getUnpublished();
        String newPublished = FrontContents.publish(newUnpublished);
        FrontContent newContent = new FrontContent(element.getElement().getId(), newPublished, newUnpublished);
        return new Document(newContent);
    }

    public final Document append(final Element element) {
        String newUnpublished = this.getDocument().getUnpublished() + element.getElement().getUnpublished();
        String newPublished = FrontContents.publish(newUnpublished);
        FrontContent newContent = new FrontContent(element.getElement().getId(), newPublished, newUnpublished);
        return new Document(newContent);
    }

    public final Document removeFirst(final Element element) {
        throw new UnsupportedOperationException();
    }

    public final Document removeAll(final Element element) {
        String newUnpublished = this.getDocument().getUnpublished().replaceAll(element.getElement().getUnpublished(), "");
        String newPublished = FrontContents.publish(newUnpublished);
        FrontContent newContent = new FrontContent(newPublished, newUnpublished);
        return new Document(newContent);
    }

    public final Document replaceAll(final Element currentElement, final Element newElement) {
        String newUnpublished = this.getDocument().getUnpublished().replaceAll(currentElement.getContent().getUnpublished(), newElement.getContent().getUnpublished());
        String newPublished = FrontContents.publish(newUnpublished);
        return new Document(new FrontContent(newPublished, newUnpublished));
    }

    @Override
    public final String print() {
        return this.document.getPublished();
    }

    @Override
    public final String toString() {
        return "Document{" + "document=" + document + '}';
    }

}
