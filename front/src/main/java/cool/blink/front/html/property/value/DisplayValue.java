package cool.blink.front.html.property.value;

public enum DisplayValue {

    auto("inline"),
    block("block"),
    flex("flex"),
    inlineBlock("inline-block"),
    inlineFlex("inline-flex"),
    inlineTable("inline-table"),
    listItem("list-item"),
    runIn("run-in"),
    table("table"),
    tableCaption("table-caption"),
    tableColumnGroup("table-column-group"),
    tableHeaderGroup("table-header-group"),
    tableFooterGroup("table-footer-group"),
    tableRowGroup("table-row-group"),
    tableCell("table-cell"),
    tableColumn("table-column"),
    tableRow("table-row"),
    none("none"),
    initial("initial"),
    inherit("inherit");

    private final String display;

    private DisplayValue(final String display) {
        this.display = display;
    }

    @Override
    public final String toString() {
        return display;
    }

}
