package cool.blink.back.database;

import cool.blink.back.database.Database.SqlDataType;

public final class Column {

    private final String name;
    private final SqlDataType sqlDataType;
    private final Integer length;
    private final Boolean primaryKey;
    private final Boolean notNull;
    private final Table table;

    public Column(final String name, final SqlDataType sqlDataType, final Integer length, final Boolean primaryKey, final Boolean notNull, final Table table) {
        this.name = name.toLowerCase();
        this.sqlDataType = sqlDataType;
        this.length = length;
        this.primaryKey = primaryKey;
        this.notNull = notNull;
        this.table = table;
    }

    public final String getName() {
        return name;
    }

    public final SqlDataType getSqlDataType() {
        return sqlDataType;
    }

    public final Integer getLength() {
        return length;
    }

    public final Boolean getPrimaryKey() {
        return primaryKey;
    }

    public final Boolean getNotNull() {
        return notNull;
    }

    public final Table getTable() {
        return table;
    }

    @Override
    public String toString() {
        return "Column{" + "name=" + name + ", sqlDataType=" + sqlDataType + ", length=" + length + ", primaryKey=" + primaryKey + ", notNull=" + notNull + '}';
    }

}
