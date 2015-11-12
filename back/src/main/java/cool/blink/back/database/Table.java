package cool.blink.back.database;

import java.util.List;

public class Table {

    private String catalog;
    private String schema;
    private String name;
    private String type;
    private String remarks;
    private Database database;
    private List<Column> columns;
    private List<Record> records;

    public Table() {
    }

    public Table(String name) {
        this.catalog = null;
        this.schema = null;
        this.name = name.toLowerCase();
        this.type = null;
        this.remarks = null;
        this.database = null;
        this.columns = null;
        this.records = null;
    }

    public Table(String catalog, String schema, String name, String type, String remarks, Database database, List<Column> columns, List<Record> records) {
        this.catalog = catalog;
        this.schema = schema;
        this.name = name.toLowerCase();
        this.type = type;
        this.remarks = remarks;
        this.database = database;
        this.columns = columns;
        this.records = records;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "Table{" + "catalog=" + catalog + ", schema=" + schema + ", name=" + name + ", type=" + type + ", remarks=" + remarks + ", database=" + database + ", columns=" + columns + ", records=" + records + '}';
    }
}
