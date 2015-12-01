package cool.blink.back.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Record {

    private final Table table;
    private final List<Cell> cells;

    public Record(final Table table) {
        this.table = table;
        this.cells = new ArrayList<>();
    }

    public Record(final Table table, final Cell... cells) {
        this.table = table;
        this.cells = Arrays.asList(cells);
    }

    public final Table getTable() {
        return table;
    }

    public final List<Cell> getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "Record{" + "cells=" + cells + '}';
    }

}
