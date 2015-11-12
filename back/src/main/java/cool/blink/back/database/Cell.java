package cool.blink.back.database;

public class Cell {

    private Record record;
    private Column column;
    private Object object;

    public Cell() {

    }

    public Cell(Record record, Column column, Object object) {
        this.record = record;
        this.column = column;
        this.object = object;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
