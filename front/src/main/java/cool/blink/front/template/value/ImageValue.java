package cool.blink.front.template.value;

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
