package cool.blink.back.database;

import java.util.List;

public abstract class Record {

    private Table table;
    private List<Cell> cells;

    public Record() {

    }

    public Record(final Table table, final List<Cell> cells) {
        this.table = table;
        this.cells = cells;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

}
