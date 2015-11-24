package cool.blink.back.database;

import cool.blink.back.database.Database_Deprecated.SqlDataType;

public class Column {

    private String name;
    private SqlDataType sqlDataType;
    private Integer length;
    private Boolean primaryKey;
    private Boolean notNull;
    private Table table;

    public Column() {

    }

    public Column(String name, SqlDataType sqlDataType, Integer length, Boolean primaryKey, Boolean notNull, Table table) {
        this.name = name.toLowerCase();
        this.sqlDataType = sqlDataType;
        this.length = length;
        this.primaryKey = primaryKey;
        this.notNull = notNull;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SqlDataType getSqlDataType() {
        return sqlDataType;
    }

    public void setSqlDataType(SqlDataType sqlDataType) {
        this.sqlDataType = sqlDataType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}
