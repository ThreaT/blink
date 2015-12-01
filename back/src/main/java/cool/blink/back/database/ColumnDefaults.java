package cool.blink.back.database;

public final class ColumnDefaults {

    public static final Boolean getDefaultNotNull(Boolean isPrimaryKey) {
        return isPrimaryKey;
    }

    public static final Boolean getDefaultPrimaryKey(String fieldName) {
        return fieldName.equalsIgnoreCase("id");
    }

    public static final Integer getDefaultVarcharLength() {
        return 30;
    }

}
