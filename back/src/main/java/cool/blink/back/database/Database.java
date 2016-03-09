package cool.blink.back.database;

import cool.blink.back.cluster.Action;
import cool.blink.back.utilities.Logs;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.back.utilities.Reflections;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * API
 *
 * <h3>Misc</h3>
 * <ul>
 * <li>public final synchronized void connect()</li>
 * <li>public final synchronized void disconnect()</li>
 * <li>public enum SqlDataType</li>
 * <li>public final SqlDataType sqlDataTypeMapper(final String type)</li>
 * <li>public final SqlDataType sqlDataMapper(final Class clazz)</li>
 * <li>public final Class javaDataMapper(final SqlDataType sqlDataType)</li>
 * <li>public final void unsafeExecute(final String sql)</li>
 * <li>public final void execute(final String preparedSql, final Parameter...
 * parameters)</li>
 * <li>public final synchronized void addUnsafeTransaction(final String
 * query)</li>
 * <li>public final synchronized void executeUnsafeBatch()</li>
 * </ul>
 *
 * <h3>Create</h3>
 * <br>
 * <ul>
 * <li>public final void createCell - Will not be implemented</li>
 * <li>public final void createRecord(final Object object)</li>
 * <li>public final void createColumn - Will not be implemented</li>
 * <li>public final void createTable(final Class clazz)</li>
 * <li>public final void createDatabase()</li>
 * </ul>
 *
 * <h3>Read</h3>
 * <br>
 * <ul>
 * <li>public final List&gt;Cell&lt; readCells - Will not be implemented</li>
 * <li>public final List&gt;Record&lt; readRecords(final Class clazz, final
 * String whereClause)</li>
 * <li>public final Column readColumn - Will not be implemented</li>
 * <li>public final Table readTable(final Class clazz)</li>
 * <li>public final Boolean tableExists(final Table table)</li>
 * <li>public final Database readDatabase - Will not be implemented</li>
 * <li>public final Boolean databaseExists()</li>
 * </ul>
 *
 * <h3>Update</h3>
 * <br>
 * <ul>
 * <li>public final void updateCell - Will not be implemented</li>
 * <li>public final void updateRecord - Will not be implemented</li>
 * <li>public final void updateColumn - Will not be implemented</li>
 * <li>public final void updateTable - Will not be implemented</li>
 * <li>public final void updateDatabase - Will not be implemented</li>
 * </ul>
 *
 * <h3>Delete</h3>
 * <br>
 * <ul>
 * <li>public final void deleteCell - Will not be implemented</li>
 * <li>public final void deleteRecord - Will not be implemented</li>
 * <li>public final void deleteColumn - Will not be implemented</li>
 * <li>public final void deleteTable(final Class clazz)</li>
 * <li>public final void deleteDatabase()</li>
 * </ul>
 */
public final class Database {

    private final String name;
    private final String destination;
    private final List<Class> tables;
    private final List<String> transactions;
    private Connection connection;

    public Database(final String name, final String destination, final Class... tables) {
        this.name = name.toLowerCase();

        //Make sure destination has ending / if not blank
        String temp;
        if ((destination == null) || ("".equals(destination)) || (destination.isEmpty())) {
            temp = destination;
        } else if (destination.charAt(destination.length()) != '/') {
            temp = destination + "/";
        } else {
            temp = destination;
        }

        this.destination = temp;
        this.tables = new ArrayList<>();
        this.transactions = new ArrayList<>();

        //Add all provided tables
        this.tables.addAll(Arrays.asList(tables));

        //Add action table
        this.tables.add(Action.class);

        try {
            createDatabase();
            for (Class clazz : this.tables) {
                createTable(clazz);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

    public final String getName() {
        return name;
    }

    public final String getDestination() {
        return destination;
    }

    public final List<Class> getTables() {
        return tables;
    }

    public final List<String> getTransactions() {
        return transactions;
    }

    public final Connection getConnection() {
        return connection;
    }

    public final void setConnection(final Connection connection) {
        this.connection = connection;
    }

    public final synchronized void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection("jdbc:derby:" + new File(this.destination + this.name).getAbsolutePath());
    }

    public final synchronized void disconnect() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public enum SqlDataType {

        BIGINT,
        BLOB,
        CHAR,
        CLOB,
        DATE,
        DECIMAL,
        DOUBLE,
        FLOAT,
        INTEGER,
        NUMERIC,
        REAL,
        SMALLINT,
        TIME,
        TIMESTAMP,
        VARCHAR
    }

    public static final SqlDataType sqlDataTypeMapper(final String type) {
        SqlDataType sqlDataType = null;
        switch (type.toUpperCase()) {
            case "BIGINT":
                sqlDataType = SqlDataType.BIGINT;
                break;
            case "BLOB":
                sqlDataType = SqlDataType.BLOB;
                break;
            case "CHAR":
                sqlDataType = SqlDataType.CHAR;
                break;
            case "CLOB":
                sqlDataType = SqlDataType.CLOB;
                break;
            case "DATE":
                sqlDataType = SqlDataType.DATE;
                break;
            case "DECIMAL":
                sqlDataType = SqlDataType.DECIMAL;
                break;
            case "DOUBLE":
                sqlDataType = SqlDataType.DOUBLE;
                break;
            case "FLOAT":
                sqlDataType = SqlDataType.FLOAT;
                break;
            case "INTEGER":
                sqlDataType = SqlDataType.INTEGER;
                break;
            case "NUMERIC":
                sqlDataType = SqlDataType.NUMERIC;
                break;
            case "REAL":
                sqlDataType = SqlDataType.REAL;
                break;
            case "SMALLINT":
                sqlDataType = SqlDataType.SMALLINT;
                break;
            case "TIME":
                sqlDataType = SqlDataType.TIME;
                break;
            case "TIMESTAMP":
                sqlDataType = SqlDataType.TIMESTAMP;
                break;
            case "VARCHAR":
                sqlDataType = SqlDataType.VARCHAR;
                break;
        }
        return sqlDataType;
    }

    public final SqlDataType sqlDataMapper(final Class clazz) {
        SqlDataType sqlDataType;
        switch (clazz.getSimpleName()) {
            case "Boolean":
                sqlDataType = SqlDataType.SMALLINT;
                break;
            case "String":
                sqlDataType = SqlDataType.VARCHAR;
                break;
            case "Integer":
                sqlDataType = SqlDataType.INTEGER;
                break;
            case "Long":
                sqlDataType = SqlDataType.BIGINT;
                break;
            case "Object":
                sqlDataType = SqlDataType.BLOB;
                break;
            case "Date":
                sqlDataType = SqlDataType.DATE;
                break;
            case "DateTime":
                sqlDataType = SqlDataType.TIMESTAMP;
                break;
            case "char":
                sqlDataType = SqlDataType.CHAR;
                break;
            default:
                sqlDataType = SqlDataType.BLOB;
                break;
        }
        return sqlDataType;
    }

    public final Class javaDataMapper(final SqlDataType sqlDataType) {
        Class clazz = null;
        switch (sqlDataType) {
            case VARCHAR:
                clazz = String.class;
                break;
            case INTEGER:
                clazz = Integer.class;
                break;
            case BLOB:
                clazz = Object.class;
                break;
            case DATE:
                clazz = java.sql.Date.class;
                break;
            case CHAR:
                clazz = char.class;
                break;
        }
        return clazz;
    }

    public final void unsafeExecute(final String sql) throws ClassNotFoundException, SQLException {
        connect();
        try (Statement statement = this.connection.createStatement()) {
            statement.execute(sql);
        }
        disconnect();
    }

    public final void execute(final PreparedEntry preparedEntry) throws ClassNotFoundException, SQLException {
        Integer totalPlaceholders = StringUtils.countMatches(preparedEntry.getSql(), "?");
        if (totalPlaceholders != preparedEntry.getParameters().size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connect();
        PreparedStatement preparedStatement = this.connection.prepareStatement(preparedEntry.getSql());
        for (Parameter parameter : preparedEntry.getParameters()) {
            switch (parameter.getType().getSimpleName().toLowerCase()) {
                case "string":
                    preparedStatement.setString(parameter.getPlaceholderIndex(), (String) parameter.getValue());
                    break;
                case "integer":
                    preparedStatement.setInt(parameter.getPlaceholderIndex(), (Integer) parameter.getValue());
                    break;
                case "date":
                    preparedStatement.setDate(parameter.getPlaceholderIndex(), (Date) parameter.getValue());
                    break;
                case "long":
                    preparedStatement.setLong(parameter.getPlaceholderIndex(), (Long) parameter.getValue());
                    break;
                default:
                    preparedStatement.setBlob(parameter.getPlaceholderIndex(), (Blob) parameter.getValue());
                    break;
            }
        }
        preparedStatement.executeUpdate();
        preparedStatement.closeOnCompletion();
        disconnect();
    }

    public final synchronized void addUnsafeTransaction(final String query) {
        this.transactions.add(query);
    }

    public final synchronized void executeUnsafeBatch() throws ClassNotFoundException, SQLException {
        try (Connection tempConnection = DriverManager.getConnection("jdbc:derby:" + new File(this.destination + this.name).getAbsolutePath())) {
            Statement statement = tempConnection.createStatement();
            tempConnection.setAutoCommit(false);
            for (String transaction : this.transactions) {
                statement.addBatch(transaction);
            }
            statement.executeBatch();
            tempConnection.commit();
        }
    }

    public final void createRecord(final Object object) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SQLException {
        //Get parameters
        List<Parameter> parameters = new ArrayList<>();
        List<Field> fields = Reflections.classToFieldsList(object.getClass());
        for (int i = 1; i < fields.size() + 1; i++) {
            if (!fields.get(i - 1).getName().equalsIgnoreCase("id")) {
                Integer placeholderIndex = parameters.size() + 1;
                Object value = PropertyUtils.getProperty(object, fields.get(i - 1).getName());
                Class type = fields.get(i - 1).getType();
                Parameter parameter = new Parameter(placeholderIndex, value, type);
                parameters.add(parameter);
            }
        }

        //Set column names
        Integer totalCols = 0;
        String preparedSql = "INSERT INTO " + object.getClass().getSimpleName().toUpperCase() + "(";
        for (int i = 1; i < fields.size() + 1; i++) {
            if (!fields.get(i - 1).getName().equalsIgnoreCase("id")) {
                preparedSql += fields.get(i - 1).getName();
                totalCols++;
                if (fields.size() > i) {
                    preparedSql += ",";
                }
            }
        }
        preparedSql += ")";

        //Set column values
        preparedSql += " " + "VALUES(";
        for (int i = 0; i < totalCols; i++) {
            if (i + 1 < totalCols) {
                preparedSql += "?,";
            } else {
                preparedSql += "?)";
            }
        }

        //Execute preparedSql
        connect();
        PreparedStatement preparedStatement = this.connection.prepareStatement(preparedSql);

        //Assign parameters to prepared statement
        for (Parameter parameter : parameters) {
            switch (parameter.getType().getSimpleName().toLowerCase()) {
                case "string":
                    preparedStatement.setString(parameter.getPlaceholderIndex(), (String) parameter.getValue());
                    break;
                case "integer":
                    if ((Integer) parameter.getValue() == null) {
                        //Do nothing - this will cause a primary key to auto-increment in most cases or reflect a null in all the others
                    } else {
                        preparedStatement.setInt(parameter.getPlaceholderIndex(), (Integer) parameter.getValue());
                    }
                    break;
                case "date":
                    preparedStatement.setDate(parameter.getPlaceholderIndex(), (Date) parameter.getValue());
                    break;
                case "long":
                    preparedStatement.setLong(parameter.getPlaceholderIndex(), (Long) parameter.getValue());
                    break;
                default:
                    preparedStatement.setBlob(parameter.getPlaceholderIndex(), (Blob) parameter.getValue());
                    break;
            }
        }
        preparedStatement.executeUpdate();
        preparedStatement.closeOnCompletion();
        disconnect();
    }

    public final void createTable(final Class clazz) throws ClassNotFoundException, SQLException, IllegalAccessException, IllegalArgumentException {
        Table table = new Table(clazz.getSimpleName());

        //Generate the column classes for clazz
        List<Field> classFields = Reflections.classToFieldsList(clazz);
        Column column;
        List<Column> columns = new ArrayList<>();
        for (Field field : classFields) {
            String columnName = field.getName();
            SqlDataType sqlDataType = sqlDataMapper(field.getType());
            Integer length = null;
            if (sqlDataType.equals(SqlDataType.VARCHAR)) {
                //30 varchar length by default
                length = ColumnDefaults.getDefaultVarcharLength();
            }
            //Primary Key by default if the field name is "id"
            Boolean primaryKey = ColumnDefaults.getDefaultPrimaryKey(field.getName());
            //Nullable by default when not a primary key
            Boolean notNull = ColumnDefaults.getDefaultNotNull(primaryKey);
            column = new Column(columnName, sqlDataType, length, primaryKey, notNull, table);
            columns.add(column);
        }

        table.getColumns().addAll(columns);
        if (tableExists(table)) {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "{0} table already exists.", clazz.getSimpleName());
        } else {
            //Create the table in the physical database
            String createStatement = "CREATE TABLE ";
            Column primaryKey = null;
            SqlDataType sqlDataType;
            createStatement += table.getName() + "(";
            for (int i = 0; i < table.getColumns().size(); i++) {
                createStatement += table.getColumns().get(i).getName();
                sqlDataType = table.getColumns().get(i).getSqlDataType();
                createStatement += " " + sqlDataType;
                if ((table.getColumns().get(i).getLength() != null) && (sqlDataType != SqlDataType.BIGINT)) {
                    createStatement += (table.getColumns().get(i).getLength() == null ? "" : " " + "(" + table.getColumns().get(i).getLength() + ")");
                }
                createStatement += (table.getColumns().get(i).getNotNull() == true ? " " + "NOT NULL" : "");
                if (table.getColumns().get(i).getPrimaryKey() == true) {
                    primaryKey = table.getColumns().get(i);
                    createStatement += " " + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
                }
                if (i == table.getColumns().size() - 1) {
                    if (primaryKey != null) {
                        createStatement += ", PRIMARY KEY (" + primaryKey.getName() + ")";
                    }
                } else {
                    createStatement += ", ";
                }
            }
            createStatement += ")";
            execute(new PreparedEntry(createStatement));
            if (tableExists(table)) {
                Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "{0} table has been created.", clazz.getSimpleName());
            } else {
                Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "There was a problem while attempting to create a physical table titled {0}.", clazz.getSimpleName());
            }
        }
    }

    public final void createDatabase() throws SQLException {
        if (databaseExists()) {
            Logger.getLogger(Database.class.getName()).log(Logs.Priority.HIGH, "Database already exists in {0}/{1}, this database will be used.", new Object[]{new File(this.getDestination()).getAbsolutePath(), this.getName()});
        } else {
            this.connection = DriverManager.getConnection("jdbc:derby:" + this.getDestination() + this.getName() + ";" + "create=true");
            disconnect();
            if (databaseExists()) {
                Logger.getLogger(Database.class.getName()).log(Logs.Priority.MEDIUM, "Database created in {0}/{1}", new Object[]{this.getDestination(), this.getName()});
            } else {
                Logger.getLogger(Database.class.getName()).log(Logs.Priority.HIGH, "There was a problem while attempting to create the physical database.");
            }
        }
    }

    public final List<Record> readRecords(final PreparedEntry preparedEntry) throws ClassNotFoundException, SQLException, InterruptedException {
        Integer totalPlaceholders = StringUtils.countMatches(preparedEntry.getSql(), "?");
        if (totalPlaceholders != preparedEntry.getParameters().size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connect();

        //Assign parameters to preparedSql
        PreparedStatement preparedStatement = this.connection.prepareStatement(preparedEntry.getSql());
        for (Parameter parameter : preparedEntry.getParameters()) {
            switch (parameter.getType().getSimpleName().toLowerCase()) {
                case "string":
                    preparedStatement.setString(parameter.getPlaceholderIndex(), (String) parameter.getValue());
                    break;
                case "integer":
                    preparedStatement.setInt(parameter.getPlaceholderIndex(), (Integer) parameter.getValue());
                    break;
                case "date":
                    preparedStatement.setDate(parameter.getPlaceholderIndex(), (Date) parameter.getValue());
                    break;
                default:
                    throw new SQLDataException("Invalid parameter type: " + parameter.toString());
            }
        }

        //Run query
        ResultSet resultSet = preparedStatement.executeQuery();

        //Get column data
        List<Column> columns = new ArrayList<>();
        ResultSetMetaData columnMeta = resultSet.getMetaData();
        Integer columnCount = columnMeta.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            String columnName = columnMeta.getColumnName(i + 1);
            SqlDataType sqlDataType = sqlDataTypeMapper((columnMeta.getColumnTypeName(i + 1)));
            Integer columnLength = columnMeta.getPrecision(i + 1);
            Boolean columnPrimaryKey = columnMeta.isAutoIncrement(i + 1);
            Boolean columnNotNull = columnMeta.isNullable(i + 1) == 0;
            columns.add(new Column(columnName, sqlDataType, columnLength, columnPrimaryKey, columnNotNull));
        }

        //Get records data from resultSet
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<Cell> cells = new ArrayList<>();
        while (resultSet.next()) {
            //Get object data
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                Object object;
                if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) == null) {
                    object = null;
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof String) {
                    object = resultSet.getString(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Integer) {
                    object = resultSet.getInt(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Date) {
                    object = resultSet.getDate(resultSetMetaData.getColumnName(i + 1));
                } else {
                    object = resultSet.getBlob(resultSetMetaData.getColumnName(i + 1));
                }
                cells.add(new Cell(null, (Column) columns.get(i), object));
            }
        }

        //Organize record data and assign records to cells and cells to records
        List<Record> records = new ArrayList<>();
        Record record = new Record();
        for (int i = 0; i < cells.size(); i++) {
            if (i % resultSetMetaData.getColumnCount() == 0) {
                record = new Record();
            }
            record.getCells().add(cells.get(i));
            cells.get(i).setRecord(record);
            if (i % resultSetMetaData.getColumnCount() == 0) {
                records.add(record);
            }
        }

        preparedStatement.closeOnCompletion();
        disconnect();
        return records;
    }

    public final List<Record> readRecords(final Class clazz, final PreparedEntry preparedEntry) throws ClassNotFoundException, SQLException, InterruptedException {
        Table table = new Table(clazz.getSimpleName());
        Integer totalPlaceholders = StringUtils.countMatches(preparedEntry.getSql(), "?");
        if (totalPlaceholders != preparedEntry.getParameters().size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connect();

        //Assign parameters to preparedSql
        PreparedStatement preparedStatement = this.connection.prepareStatement(preparedEntry.getSql());
        for (Parameter parameter : preparedEntry.getParameters()) {
            switch (parameter.getType().getSimpleName().toLowerCase()) {
                case "string":
                    preparedStatement.setString(parameter.getPlaceholderIndex(), (String) parameter.getValue());
                    break;
                case "integer":
                    preparedStatement.setInt(parameter.getPlaceholderIndex(), (Integer) parameter.getValue());
                    break;
                case "date":
                    preparedStatement.setDate(parameter.getPlaceholderIndex(), (Date) parameter.getValue());
                    break;
                default:
                    throw new SQLDataException("Invalid parameter type: " + parameter.toString());
            }
        }

        //Run query
        ResultSet resultSet = preparedStatement.executeQuery();

        //Get column data
        List<Column> columns = new ArrayList<>();
        ResultSetMetaData columnMeta = resultSet.getMetaData();
        Integer columnCount = columnMeta.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            String columnName = columnMeta.getColumnName(i + 1);
            SqlDataType sqlDataType = sqlDataTypeMapper((columnMeta.getColumnTypeName(i + 1)));
            Integer columnLength = columnMeta.getPrecision(i + 1);
            Boolean columnPrimaryKey = columnMeta.isAutoIncrement(i + 1);
            Boolean columnNotNull = columnMeta.isNullable(i + 1) == 0;
            columns.add(new Column(columnName, sqlDataType, columnLength, columnPrimaryKey, columnNotNull, table));
        }

        //Get record data from resultSet
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<Cell> cells = new ArrayList<>();
        while (resultSet.next()) {
            //Get object data
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                Object object;
                if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) == null) {
                    object = null;
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof String) {
                    object = resultSet.getString(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Integer) {
                    object = resultSet.getInt(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Date) {
                    object = resultSet.getDate(resultSetMetaData.getColumnName(i + 1));
                } else {
                    object = resultSet.getBlob(resultSetMetaData.getColumnName(i + 1));
                }
                cells.add(new Cell(null, (Column) columns.get(i), object));
            }
        }

        //Organize record data and assign records to cells and cells to records
        List<Record> records = new ArrayList<>();
        Record record = new Record(table);
        for (int i = 0; i < cells.size(); i++) {
            if (i % resultSetMetaData.getColumnCount() == 0) {
                record = new Record(table);
            }
            record.getCells().add(cells.get(i));
            cells.get(i).setRecord(record);
            records.add(record);
        }

        preparedStatement.closeOnCompletion();
        disconnect();
        return records;
    }

    public final Table readTable(final Class clazz) throws ClassNotFoundException, SQLException, InterruptedException {
        Table table = new Table(clazz.getSimpleName());
        if (!tableExists(table)) {
            return null;
        } else {
            connect();
            DatabaseMetaData tableMeta = this.connection.getMetaData();
            try (ResultSet tableResultSet = tableMeta.getTables(null, null, null, new String[]{"TABLE"})) {
                while (tableResultSet.next()) {
                    if (tableResultSet.getString("TABLE_NAME").equalsIgnoreCase(table.getName())) {
                        List<Column> tableColumns = new ArrayList<>();
                        DatabaseMetaData columnMeta = this.connection.getMetaData();
                        ResultSet columnPrimaryKeysSet;
                        try (ResultSet columnResultSet = columnMeta.getColumns(null, null, table.getName(), null)) {
                            while (columnResultSet.next()) {
                                String columnName = columnResultSet.getString("COLUMN_NAME");
                                SqlDataType sqlDataType = sqlDataTypeMapper((columnResultSet.getString("TYPE_NAME")));
                                Integer columnLength = columnResultSet.getInt("COLUMN_SIZE");
                                Boolean columnPrimaryKey = false;
                                columnPrimaryKeysSet = columnMeta.getPrimaryKeys(null, null, table.getName());
                                while (columnPrimaryKeysSet.next()) {
                                    if (columnPrimaryKeysSet.getString(4).equalsIgnoreCase(columnName)) {
                                        columnPrimaryKey = true;
                                    }
                                }
                                Boolean columnNotNull = columnResultSet.getString("NULLABLE").equals("0");
                                Column column = new Column(columnName, sqlDataType, columnLength, columnPrimaryKey, columnNotNull, table);
                                tableColumns.add(column);
                            }
                        }
                        List<Record> tableRecords = this.readRecords(clazz, new PreparedEntry("SELECT * FROM " + table.getName()));
                        String tableCatalog = tableResultSet.getString("TABLE_CAT");
                        String tableSchema = tableResultSet.getString("TABLE_SCHEM");
                        String tableName = tableResultSet.getString("TABLE_NAME");
                        String tableType = tableResultSet.getString("TABLE_TYPE");
                        String tableRemarks = tableResultSet.getString("REMARKS");
                        table = new Table(tableCatalog, tableSchema, tableName, tableType, tableRemarks, this, tableColumns, tableRecords);
                        break;
                    }
                }
                tableResultSet.close();
            }
            return table;
        }
    }

    public final Boolean tableExists(final Table table) throws SQLException, ClassNotFoundException {
        connect();
        DatabaseMetaData databaseMetaData = this.connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, "%", null);
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").equalsIgnoreCase(table.getName())) {
                return true;
            }
        }
        disconnect();
        return false;
    }

    public final Boolean databaseExists() {
        File databaseFolder = new File(this.getDestination() + this.getName());
        return databaseFolder.exists();
    }

    public final void deleteTable(final Class clazz) throws ClassNotFoundException, SQLException {
        connect();
        Statement statement = this.connection.createStatement();
        String deleteStatement = "DROP TABLE " + clazz.getSimpleName().toUpperCase();
        statement.executeUpdate(deleteStatement);
        if (!tableExists(new Table(clazz.getSimpleName()))) {
            Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "{0} was deleted.", clazz.getSimpleName());
        } else {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "{0} could not be deleted.", clazz.getSimpleName());
        }
        disconnect();
    }

    public final void deleteDatabase() throws IOException, SQLException {
        disconnect();
        File databaseLog = new File(this.destination + "derby.log");
        databaseLog.delete();
        File database = new File(this.destination + this.name);
        FileUtils.deleteDirectory(database);
    }
}
