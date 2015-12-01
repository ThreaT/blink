package cool.blink.back.database;

public final class Cell {

    private Record record;
    private Column column;
    private Object object;

    public Cell(final Record record, final Column column, final Object object) {
        this.record = record;
        this.column = column;
        this.object = object;
    }

    public final Record getRecord() {
        return record;
    }

    public final void setRecord(final Record record) {
        this.record = record;
    }

    public final Column getColumn() {
        return column;
    }

    public final void setColumn(final Column column) {
        this.column = column;
    }

    public final Object getObject() {
        return object;
    }

    public final void setObject(final Object object) {
        this.object = object;
    }

    @Override
    public final String toString() {
        return "Cell{" + "record=" + record + ", column=" + column + ", object=" + object + '}';
    }

}
