package cool.blink.front.css.template;

public enum ImageValue {

    png("png"),
    jpg("jpg"),
    jpeg("jpeg"),
    gif("gif"),
    bmp("bmp");

    private final String image;

    private ImageValue(final String image) {
        this.image = image;
    }

    @Override
    public final String toString() {
        return image;
    }

}
