package cool.blink.front.utilities;

public final class Elements {

    public enum TagType {

        fertile,
        infertile,
        docTypeHybrid,
        metaHybrid
    }

    public enum TagComponent {

        docTypeHybridOpen("<!"),
        Open("<"),
        Close(">"),
        infertileClose(" />"),
        fertileClose("</");

        private final String tag;

        private TagComponent(String tag) {
            this.tag = tag;
        }

        @Override
        public String toString() {
            return tag;
        }

    }

}
