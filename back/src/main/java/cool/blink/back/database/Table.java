package cool.blink.back.database;

import java.util.ArrayList;
import java.util.List;

public final class Table {

    private final String catalog;
    private final String schema;
    private final String name;
    private final String type;
    private final String remarks;
    private final Database database;
    private final List<Column> columns;
    private final List<Record> records;

    public Table(final String name) {
        this.catalog = null;
        this.schema = null;
        this.name = name.toUpperCase();
        this.type = null;
        this.remarks = null;
        this.database = null;
        this.columns = new ArrayList<>();
        this.records = new ArrayList<>();
    }

    public Table(final String catalog, final String schema, final String name, final String type, final String remarks, final Database database, final List<Column> columns, final List<Record> records) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name.toUpperCase();
        this.type = type;
        this.remarks = remarks;
        this.database = database;
        this.columns = columns;
        this.records = records;
    }

    public final String getCatalog() {
        return catalog;
    }

    public final String getSchema() {
        return schema;
    }

    public final String getName() {
        return name;
    }

    public final String getType() {
        return type;
    }

    public final String getRemarks() {
        return remarks;
    }

    public final List<Column> getColumns() {
        return columns;
    }

    public final List<Record> getRecords() {
        return records;
    }

    public final Database getDatabase() {
        return database;
    }

    @Override
    public final String toString() {
        return "Table{" + "catalog=" + catalog + ", schema=" + schema + ", name=" + name + ", type=" + type + ", remarks=" + remarks + ", database=" + database + ", columns=" + columns + ", records=" + records + '}';
    }
}
