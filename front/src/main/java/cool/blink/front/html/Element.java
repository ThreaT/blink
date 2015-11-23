package cool.blink.front.html;

import cool.blink.front.FrontContent;
import cool.blink.front.html.exception.InfertileElementException;
import cool.blink.front.utilities.Elements;
import cool.blink.front.utilities.Elements.TagType;
import cool.blink.front.utilities.FrontContents;
import cool.blink.front.utilities.Logs.CustomLevel;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public abstract class Element implements FrontContent.Api {

    private final TagType tagType;
    private final String openingTagStart;
    private final String openingTagTitle;
    private final FrontContent attributes;
    private final String openingTagEnd;
    private final FrontContent content;
    private final String closingTagStart;
    private final String closingTagTitle;
    private final String closingTagEnd;
    private final FrontContent element;

    public Element(final TagType tagType, final FrontContent attributes, final FrontContent content) {
        this.tagType = tagType;
        switch (tagType) {
            case fertile:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
            case infertile:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
            case docTypeHybrid:
                this.openingTagStart = Elements.TagComponent.docTypeHybridOpen.toString();
                break;
            case metaHybrid:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
            default:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
        }
        this.openingTagTitle = this.getClass().getSimpleName().toLowerCase();
        this.attributes = attributes;
        switch (tagType) {
            case fertile:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
            case infertile:
                this.openingTagEnd = Elements.TagComponent.infertileClose.toString();
                break;
            case docTypeHybrid:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
            case metaHybrid:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
            default:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
        }
        this.content = content;
        if (tagType.equals(TagType.fertile)) {
            this.closingTagStart = Elements.TagComponent.fertileClose.toString();
            this.closingTagTitle = this.getClass().getSimpleName().toLowerCase();
            this.closingTagEnd = Elements.TagComponent.Close.toString();
        } else {
            this.closingTagStart = "";
            this.closingTagTitle = "";
            this.closingTagEnd = "";
        }
        String unpublished = this.openingTagStart + this.openingTagTitle + this.attributes.getUnpublished() + this.openingTagEnd + this.content.getUnpublished() + this.closingTagStart + this.closingTagTitle + this.closingTagEnd;
        String published = FrontContents.publish(unpublished);
        this.element = new FrontContent(published, unpublished);
    }

    public Element(final TagType tagType, final String tagTitle, final FrontContent attributes, final FrontContent content) {
        this.tagType = tagType;
        switch (tagType) {
            case fertile:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
            case infertile:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
            case docTypeHybrid:
                this.openingTagStart = Elements.TagComponent.docTypeHybridOpen.toString();
                break;
            case metaHybrid:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
            default:
                this.openingTagStart = Elements.TagComponent.Open.toString();
                break;
        }
        this.openingTagTitle = tagTitle;
        this.attributes = attributes;
        switch (tagType) {
            case fertile:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
            case infertile:
                this.openingTagEnd = Elements.TagComponent.infertileClose.toString();
                break;
            case docTypeHybrid:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
            case metaHybrid:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
            default:
                this.openingTagEnd = Elements.TagComponent.Close.toString();
                break;
        }
        this.content = content;
        if (tagType.equals(TagType.fertile)) {
            this.closingTagStart = Elements.TagComponent.fertileClose.toString();
            this.closingTagTitle = tagTitle;
            this.closingTagEnd = Elements.TagComponent.Close.toString();
        } else {
            this.closingTagStart = "";
            this.closingTagTitle = "";
            this.closingTagEnd = "";
        }
        this.element = new FrontContent(this.openingTagStart + this.openingTagTitle + this.attributes.getPublished() + this.openingTagEnd + this.content.getPublished() + this.closingTagStart + this.closingTagTitle + this.closingTagEnd);
    }

    public final TagType getTagType() {
        return tagType;
    }

    public final String getOpeningTagStart() {
        return openingTagStart;
    }

    public final String getOpeningTagTitle() {
        return openingTagTitle;
    }

    public final FrontContent getAttributes() {
        return attributes;
    }

    public final String getOpeningTagEnd() {
        return openingTagEnd;
    }

    public final FrontContent getContent() {
        return content;
    }

    public final String getClosingTagStart() {
        return closingTagStart;
    }

    public final String getClosingTagTitle() {
        return closingTagTitle;
    }

    public final String getClosingTagEnd() {
        return closingTagEnd;
    }

    public FrontContent getElement() {
        return element;
    }

    public final Point find(final FrontContent content) {
        return null;
    }

    public final Element prepend(final Attribute attribute) {
        FrontContent newAttributes;
        Element newElement = null;
        try {
            newAttributes = new FrontContent(attribute.getAttribute().getPublished() + this.getAttributes().getPublished());
            newElement = this.getClass().getDeclaredConstructor(FrontContent.class, FrontContent.class).newInstance(newAttributes, this.getContent());
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Element.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        return newElement;
    }

    public final Element append(final Attribute attribute) {
        FrontContent newAttributes;
        Element newElement = null;
        try {
            newAttributes = new FrontContent(this.getAttributes().getPublished() + attribute.getAttribute().getPublished());
            newElement = this.getClass().getDeclaredConstructor(FrontContent.class, FrontContent.class).newInstance(newAttributes, this.getContent());
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Element.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        return newElement;
    }

    public final Element prepend(final Element element) {
        if (this.getTagType().equals(TagType.infertile)) {
            throw new InfertileElementException("Cannot prepend element to infertile tag type");
        }
        Element currentElement = null;
        try {
            String newUnpublished = element.getElement().getUnpublished() + this.getContent().getUnpublished();
            String newPublished = FrontContents.publish(newUnpublished);
            FrontContent newContent = new FrontContent(newPublished, newUnpublished);
            currentElement = this.getClass().getDeclaredConstructor(FrontContent.class, FrontContent.class).newInstance(this.getAttributes(), newContent);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Element.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        return currentElement;
    }

    public final Element append(final Element element) {
        if (this.getTagType().equals(TagType.infertile)) {
            throw new InfertileElementException("Cannot append element to infertile tag type");
        }
        Element currentElement = null;
        try {
            String newUnpublished = this.getContent().getUnpublished() + element.getElement().getUnpublished();
            String newPublished = FrontContents.publish(newUnpublished);
            FrontContent newContent = new FrontContent(newPublished, newUnpublished);
            currentElement = this.getClass().getDeclaredConstructor(FrontContent.class, FrontContent.class).newInstance(this.getAttributes(), newContent);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Element.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        return currentElement;
    }

    public final Element prepend(final Text text) {
        if (this.getTagType().equals(TagType.infertile)) {
            throw new InfertileElementException("Cannot prepend element to infertile tag type");
        }
        Element newElement = null;
        try {
            String newUnpublished = text.getText().getUnpublished() + this.getContent().getUnpublished();
            String newPublished = FrontContents.publish(newUnpublished);
            FrontContent newContent = new FrontContent(newPublished, newUnpublished);
            newElement = this.getClass().getDeclaredConstructor(FrontContent.class, FrontContent.class).newInstance(this.getAttributes(), newContent);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Element.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        return newElement;
    }

    public final Element append(final Text text) {
        if (this.getTagType().equals(TagType.infertile)) {
            throw new InfertileElementException("Cannot append element to infertile tag type");
        }
        Element newElement = null;
        try {
            String newUnpublished = this.getContent().getUnpublished() + text.getText().getUnpublished();
            String newPublished = FrontContents.publish(newUnpublished);
            FrontContent newContent = new FrontContent(newPublished, newUnpublished);
            newElement = this.getClass().getDeclaredConstructor(FrontContent.class, FrontContent.class).newInstance(this.getAttributes(), newContent);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Element.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        return newElement;
    }

    public final FrontContent remove(final FrontContent content) {
        return null;
    }

    public final Element replaceAll(final Element oldElement, final Element newElement) {
        Element currentElement = null;
        try {
            String newUnpublished = this.getContent().getUnpublished().replaceAll(oldElement.getElement().getUnpublished(), newElement.getElement().getUnpublished());
            String newPublished = FrontContents.publish(newUnpublished);
            FrontContent newContent = new FrontContent(newUnpublished, newPublished);
            currentElement = this.getClass().getDeclaredConstructor(FrontContent.class, FrontContent.class).newInstance(this.getAttributes(), newContent);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Element.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
        return currentElement;
    }

    @Override
    public final String print() {
        return this.openingTagStart + this.openingTagTitle + this.attributes.getPublished() + this.openingTagEnd + this.content.getPublished() + this.closingTagStart + this.closingTagTitle + this.closingTagEnd;
    }

    @Override
    public final String toString() {
        return "Element{" + "tagType=" + tagType + ", openingTagStart=" + openingTagStart + ", openingTagTitle=" + openingTagTitle + ", attributes=" + attributes + ", openingTagEnd=" + openingTagEnd + ", content=" + content + ", closingTagStart=" + closingTagStart + ", closingTagTitle=" + closingTagTitle + ", closingTagEnd=" + closingTagEnd + ", element=" + element + '}';
    }

}
