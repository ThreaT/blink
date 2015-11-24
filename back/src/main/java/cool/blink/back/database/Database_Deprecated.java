package cool.blink.back.database;

import cool.blink.back.cluster.Action;
import cool.blink.back.utilities.Logs.Priority;
import cool.blink.back.utilities.Reflections;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public final class Database_Deprecated {

    private String name;
    private String destination;
    private List<Class> tables;
    private Connection connection;

    public Database_Deprecated() {

    }

    public Database_Deprecated(String name, String destination) {
        this.name = name.toLowerCase();
        this.destination = destination;
        this.tables = new ArrayList<>();

        //Add action table
        this.tables.add(Action.class);

        try {
            createPhysicalDatabase();
            createAllPhysicalTables();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(Database_Deprecated.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Database_Deprecated(String name, String destination, Class... tables) {
        this.name = name.toLowerCase();
        this.destination = new File(destination).getAbsolutePath();
        this.tables = new ArrayList<>();
        this.tables.addAll(Arrays.asList(tables));

        //Add action table
        this.tables.add(Action.class);

        try {
            createPhysicalDatabase();
            createAllPhysicalTables();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(Database_Deprecated.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Class> getTables() {
        return tables;
    }

    public void setTables(List<Class> tables) {
        this.tables = tables;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public synchronized void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        connection = DriverManager.getConnection("jdbc:derby:" + this.destination + "/" + this.name);
    }

    public synchronized void disconnect() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    /**
     * @param database database
     * @return Database containing all data in the physical database
     */
//    public Database_Deprecated convertPhysicalDatabaseToVirtualDatabase(Database_Deprecated database) {
//        if (!physicalDatabaseExists(database)) {
//            return null;
//        } else {
//            return null;
//        }
//    }
    /**
     * @param database database
     * @return Database containing all data in the physical database
     */
//    public Database_Deprecated convertVirtualDatabaseToPhysicalDatabase(Database_Deprecated database) {
//        if (!physicalDatabaseExists(database)) {
//            return null;
//        } else {
//            return null;
//        }
//    }
    /**
     * @param record record
     * @return Record
     * @throws java.sql.SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public Record convertPhysicalRecordToVirtualRecord(Record record) throws SQLException, ClassNotFoundException {
        if ((!physicalTableExists(record.getTable())) || (!physicalColumnsExist(record.getCells())) || (!physicalRecordExists(record))) {
            return null;
        } else {
            return null;
        }
    }

    /**
     * @param record record
     * @return Record containing all data in the physical record
     * @throws java.sql.SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     * @throws java.lang.IllegalAccessException IllegalAccessException
     */
    public Record convertVirtualRecordToPhysicalRecord(Record record) throws SQLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException {
        if ((!physicalTableExists(record.getTable())) || (!physicalColumnsExist(record.getCells())) || (!physicalRecordExists(record))) {
            return null;
        } else {
            return null;
        }
    }

    /**
     * @param record A virtual record with fields and values
     * @return A virtual copy of the physical record also containing the table
     * and cell data
     * @throws IllegalAccessException IllegalAccessException
     * @throws IllegalArgumentException IllegalArgumentException
     * @throws InstantiationException InstantiationException
     */
    public Record populateRecordDatabaseAndTableAndCells(Record record) throws IllegalAccessException, IllegalArgumentException, InstantiationException {
        Table table = new Table(record.getClass().getSimpleName());
        table.setDatabase(this);
        record.setTable(table);
        List<Cell> cells = new ArrayList<>();
        List<Field> fields = Reflections.classToFieldsList(record.getClass());
        for (Field field : fields) {
            String columnName = field.getName();
            SqlDataType sqlDataType = sqlDataMapper(field.getType());
            Integer length = null;
            if (sqlDataType.equals(SqlDataType.VARCHAR)) {
                length = ColumnDefaults.getDefaultVarcharLength();
            }
            Boolean primaryKey = ColumnDefaults.getDefaultPrimaryKey(field.getName());
            Boolean notNull = ColumnDefaults.getDefaultNotNull();
            Column column = new Column(columnName, sqlDataType, length, primaryKey, notNull, table);
            field.setAccessible(true);
            Cell cell = new Cell(record, column, field.get(record));
            cells.add(cell);
        }
        record.setCells(cells);
        return record;
    }

    /**
     * @param column column
     * @return Table containing all data in the physical table
     * @throws java.sql.SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public Column convertPhysicalColumnToVirtualColumn(Column column) throws SQLException, ClassNotFoundException {
        if ((!physicalTableExists(column.getTable())) || (!physicalColumnExists(column))) {
            return null;
        } else {
            return null;
        }
    }

    /**
     * @param column column
     * @return Table containing all data in the physical table
     * @throws java.sql.SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public Column convertVirtualColumnToPhysicalColumn(Column column) throws SQLException, ClassNotFoundException {
        if ((!physicalTableExists(column.getTable())) || (!physicalColumnExists(column))) {
            return null;
        } else {
            return null;
        }
    }

    public Cell convertPhysicalCellToVirtualCell(Cell cell) throws SQLException, ClassNotFoundException {
        if ((!physicalTableExists(cell.getRecord().getTable())) || (!physicalColumnExists(cell.getColumn())) || (!physicalRecordExists(cell.getRecord())) || (!physicalColumnExists(cell.getColumn()))) {
            return null;
        } else {
            return null;
        }
    }

    public Cell convertVirtualCellToPhysicalCell(Cell cell) throws SQLException, ClassNotFoundException {
        if ((!physicalTableExists(cell.getRecord().getTable())) || (!physicalColumnExists(cell.getColumn())) || (!physicalRecordExists(cell.getRecord())) || (!physicalColumnExists(cell.getColumn()))) {
            return null;
        } else {
            return null;
        }
    }

    /**
     * @throws SQLException If there is an issue while the database is
     * completing the operation
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     * @throws java.lang.IllegalAccessException IllegalAccessException
     * @throws java.lang.InstantiationException InstantiationException
     */
    public void createPhysicalDatabase() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (physicalDatabaseExists(this)) {
            Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.HIGH, "Database already exists in {0}/{1}, this database will be used.", new Object[]{this.getDestination(), this.getName()});
        } else {
            connection = DriverManager.getConnection("jdbc:derby:" + this.getDestination() + "/" + this.getName() + ";" + "create=true");
            disconnect();
            if (physicalDatabaseExists(this)) {
                Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.MEDIUM, "Database created in {0}/{1}", new Object[]{this.getDestination(), this.getName()});
            } else {
                Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.HIGH, "There was a problem while attempting to create the physical database.");
            }
        }
    }

    public Boolean physicalDatabaseExists(Database_Deprecated database) {
        File databaseFile = new File(database.getDestination() + "/" + database.getName());
        return databaseFile.exists();
    }

    public void renamePhysicalDatabase(Database_Deprecated database, String name) {

    }

    public void deletePhysicalDatabase(String filepath) throws IOException, SQLException {
        disconnect();
        File databaseLog = new File(filepath + "/" + "derby.log");
        databaseLog.delete();
        File database = new File(filepath + "/" + name);
        FileUtils.deleteDirectory(database);
    }

    public void createPhysicalTable(Table table) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
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
            }
            if (i == table.getColumns().size() - 1) {
                if (primaryKey != null) {
                    createStatement += ", PRIMARY KEY (" + primaryKey.getName() + ")";
                }
            } else {
                createStatement += ", ";
            }
        }
        //TODO is the below necessary?
        createStatement += ")";
        System.out.println("createStatement: " + createStatement);
        statement(createStatement);
    }

    public void createAllPhysicalTables() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, SQLException, InstantiationException {
        for (Class tableClass : this.tables) {
            Table table = new Table(tableClass.getSimpleName());
            List<Column> columns = this.listVirtualColumns(tableClass);
            table.setColumns(columns);
            if (physicalTableExists(table)) {
                Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.HIGH, "{0} table already exists.", tableClass.getSimpleName());
            } else {
                this.createPhysicalTable(table);
                if (physicalTableExists(table)) {
                    Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.MEDIUM, "{0} table has been created.", tableClass.getSimpleName());
                } else {
                    Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.HIGH, "There was a problem while attempting to create a physical table titled {0}.", tableClass.getSimpleName());
                }
            }
        }
    }

    public Boolean physicalTableExists(Table table) throws SQLException, ClassNotFoundException {
        connect();
        DatabaseMetaData databaseMetaData = this.connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, table.getName().toUpperCase(), null);
        Boolean exists = resultSet.next();
        disconnect();
        return exists;
    }

    public Boolean physicalTableExists(Class clazz) throws ClassNotFoundException, SQLException {
        connect();
        DatabaseMetaData databaseMetaData = this.connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, clazz.getSimpleName().toUpperCase(), null);
        Boolean exists = resultSet.next();
        disconnect();
        return exists;
    }

    /**
     * @param table table
     * @return Table containing all data in the physical table
     * @throws java.sql.SQLException If there is an issue while the database is
     * completing the operation
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public Table readPhysicalTable(Table table) throws SQLException, ClassNotFoundException {
        if (!physicalTableExists(table)) {
            return null;
        } else {
            connect();
            DatabaseMetaData tableMeta = this.connection.getMetaData();
            try (ResultSet tableResultSet = tableMeta.getTables(null, null, null, new String[]{"TABLE"})) {
                while (tableResultSet.next()) {
                    if (tableResultSet.getString("TABLE_NAME").equalsIgnoreCase(table.getName())) {
                        table = new Table();
                        table.setCatalog(tableResultSet.getString("TABLE_CAT"));
                        table.setSchema(tableResultSet.getString("TABLE_SCHEM"));
                        table.setName(tableResultSet.getString("TABLE_NAME"));
                        table.setType(tableResultSet.getString("TABLE_TYPE"));
                        table.setRemarks(tableResultSet.getString("REMARKS"));
                        List<Column> columns2 = new ArrayList<>();
                        DatabaseMetaData columnMeta = this.connection.getMetaData();
                        try (ResultSet columnResultSet = columnMeta.getColumns(null, null, table.getName(), null)) {
                            while (columnResultSet.next()) {
                                Column column = new Column();
                                column.setName(columnResultSet.getString("COLUMN_NAME"));
                                column.setSqlDataType(sqlDataTypeMapper((columnResultSet.getString("TYPE_NAME"))));
                                column.setLength(columnResultSet.getInt("COLUMN_SIZE"));
                                column.setNotNull(columnResultSet.getString("NULLABLE").equals("0"));
                                columns2.add(column);
                            }
                        }
                        table.setColumns(columns2);
                        break;
                    }
                }
                tableResultSet.close();
            }
            return table;
        }
    }

    public List<String> listAllPhysicalTableNames() throws SQLException, ClassNotFoundException {
        List<String> allTableNames;
        connect();
        DatabaseMetaData meta = this.connection.getMetaData();
        try (ResultSet resultSet = meta.getTables(null, null, null, new String[]{"TABLE"})) {
            allTableNames = new ArrayList<>();
            while (resultSet.next()) {
                allTableNames.add(resultSet.getString("TABLE_NAME"));
            }
        }
        disconnect();
        return allTableNames;
    }

    public List<Table> listAllPhysicalTables() throws SQLException, ClassNotFoundException {
        List<Table> allTables;
        connect();
        DatabaseMetaData tableMeta = this.connection.getMetaData();
        try (ResultSet tableResultSet = tableMeta.getTables(null, null, null, new String[]{"TABLE"})) {
            allTables = new ArrayList<>();
            while (tableResultSet.next()) {
                Table table = new Table();
                table.setCatalog(tableResultSet.getString("TABLE_CAT"));
                table.setSchema(tableResultSet.getString("TABLE_SCHEM"));
                table.setName(tableResultSet.getString("TABLE_NAME"));
                table.setType(tableResultSet.getString("TABLE_TYPE"));
                table.setRemarks(tableResultSet.getString("REMARKS"));
                List<Column> columns2 = new ArrayList<>();
                DatabaseMetaData columnMeta = this.connection.getMetaData();
                try (ResultSet columnResultSet = columnMeta.getColumns(null, null, table.getName(), null)) {
                    while (columnResultSet.next()) {
                        Column column = new Column();
                        column.setName(columnResultSet.getString("COLUMN_NAME"));
                        column.setSqlDataType(sqlDataTypeMapper((columnResultSet.getString("TYPE_NAME"))));
                        column.setLength(columnResultSet.getInt("COLUMN_SIZE"));
                        column.setNotNull(columnResultSet.getString("NULLABLE").equals("0"));
                        columns2.add(column);
                    }
                }
                table.setColumns(columns2);
                allTables.add(table);
            }
            tableResultSet.close();
        }
        disconnect();
        return allTables;
    }

    public void renamePhysicalTable() {

    }

    public void deletePhysicalTable(String databaseName, String tableName) throws SQLException, ClassNotFoundException {
        connect();
        Statement statement = this.connection.createStatement();
        String deleteStatement = "DROP TABLE " + tableName + ";";
        statement.executeUpdate(deleteStatement);
        disconnect();
    }

    /**
     * Creates a physical record in a physical table
     *
     * @param record record
     * @return String String
     * @throws SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     * @throws java.lang.IllegalAccessException IllegalAccessException
     * @throws java.lang.InstantiationException InstantiationException
     */
//    public String createPhysicalRecord(Record record) throws SQLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InstantiationException {
//        //Create and persist an action and then execute the action
//        String sql = "INSERT INTO" + " " + record.getTable().getName() + " " + "(";
//        sql = record.getCells().stream().map((cell) -> cell.getColumn().getName() + "," + " ").reduce(sql, String::concat);
//        sql = sql.substring(0, sql.lastIndexOf(","));
//        sql += " ";
//        sql += ")" + " " + " VALUES" + " " + "(";
//        sql = record.getCells().stream().map((_item) -> "?" + "," + " ").reduce(sql, String::concat);
//        sql = sql.substring(0, sql.lastIndexOf(","));
//        sql += " ";
//        sql += ")";
//        List<Database.Parameter> parameters = new ArrayList<>();
//        for (int i = 0; i < record.getCells().size(); i++) {
//            Parameter parameter = new Parameter(i + 1, record.getCells().get(i).getObject(), record.getCells().get(i).getObject().getClass());
//            parameters.add(parameter);
//        }
//        record.getTable().getDatabase().preparedStatementUpdate(sql, parameters);
//        return sql;
//    }
    public void createPhysicalRecord(Object object) throws ClassNotFoundException, SQLException {
        Class clazz = object.getClass();
        if (!physicalTableExists(clazz)) {
            throw new SQLException("Table for " + clazz.getName().toUpperCase() + " does not exist.");
        }

    }

    public Boolean physicalRecordExists(String tableName, String columnName, Object recordIdentifierValue) {
        return null;
    }

    public Boolean physicalRecordExists(Record record) {
        return null;
    }

    public Record readPhysicalRecord(Record record) {
        return null;
    }

    /**
     *
     * @param database database
     * @param table table
     * @param cells cells
     * @param record record
     * @return Reads this record and ones similar to it from a table
     * @throws java.sql.SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public List<Record> listPhysicalRecords(Database_Deprecated database, Table table, List<Cell> cells, Record record) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " + " ";
        sql = cells.stream().map((cell) -> cell.getColumn().getName() + "," + " ").reduce(sql, String::concat);
        sql = sql.substring(0, sql.lastIndexOf(","));
        sql += " ";
        sql += "FROM" + " " + table.getName() + " " + "WHERE" + " " + cells.get(0).getColumn().getName() + " " + "=" + " " + "?";
        for (int i = 1; i < cells.size(); i++) {
            sql += "AND" + " " + cells.get(i).getColumn().getName() + " " + "=" + " " + "?" + " ";
        }
        sql = sql.substring(0, sql.lastIndexOf(","));
        sql += " ";
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            Parameter parameter = new Parameter(i, cells.get(i).getObject(), cells.get(i).getObject().getClass());
            parameters.add(parameter);
        }
        ResultSet resultSet = database.preparedStatementSelect(sql, parameters);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        List<Record> records = new ArrayList<>();
        while (resultSet.next()) {
            List<Cell> cells2 = new ArrayList<>();
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                Cell cell = new Cell();
                if (cells.get(i).getObject() instanceof String) {
                    cell.setObject(resultSet.getString(cells.get(i).getColumn().getName()));
                } else if (cells.get(i).getObject() instanceof Integer) {
                    cell.setObject(resultSet.getInt(cells.get(i).getColumn().getName()));
                } else if (cells.get(i).getObject() instanceof Object) {
                    cell.setObject(resultSet.getBlob(cells.get(i).getColumn().getName()));
                } else if (cells.get(i).getObject() instanceof Date) {
                    cell.setObject(resultSet.getDate(cells.get(i).getColumn().getName()));
                }
                cells2.add(cell);
            }
            record.setTable(table);
            record.setCells(cells2);
            records.add(record);
        }
        return records;
    }

    public void updatePhysicalRecord() {

    }

    public void deletePhysicalRecord() {

    }

    private List<Column> createVirtualColumnsFromClass(Class clazz) throws IllegalAccessException, IllegalArgumentException, SecurityException {
        List<Column> allColumns = new ArrayList<>();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Column column = new Column();
            column.setName(field.getName());
            column.setSqlDataType(sqlDataMapper(field.getType()));
            column.setNotNull(ColumnDefaults.getDefaultNotNull());
            column.setPrimaryKey(ColumnDefaults.getDefaultPrimaryKey(field.getName()));
            allColumns.add(column);
        }
        return allColumns;
    }

    public void createPhysicalColumn() {
        //ALTER TABLE table_name ADD column_name datatype
    }

    public List<Column> listPhysicalColumns(Database_Deprecated database, String tableName) throws SQLException, ClassNotFoundException {
        connect();
        List<Column> columns = new ArrayList<>();
        DatabaseMetaData columnMeta = database.getConnection().getMetaData();
        try (ResultSet columnResultSet = columnMeta.getColumns(null, null, tableName, null)) {
            while (columnResultSet.next()) {
                Column column = new Column();
                column.setName(columnResultSet.getString("COLUMN_NAME"));
                column.setSqlDataType(sqlDataTypeMapper((columnResultSet.getString("TYPE_NAME"))));
                column.setLength(columnResultSet.getInt("COLUMN_SIZE"));
                column.setNotNull(columnResultSet.getString("NULLABLE").equals("0"));
                columns.add(column);
            }
        }
        disconnect();
        return columns;
    }

    public List<Column> listVirtualColumns(Class clazz) throws IllegalAccessException, IllegalArgumentException, ClassNotFoundException {
        List<Field> classFields = Reflections.classToFieldsList(clazz);
        Column column;
        List<Column> columns = new ArrayList<>();
        for (Field field : classFields) {
            String columnName = field.getName();
            SqlDataType sqlDataType = sqlDataMapper(field.getType());
            Integer length = null;
            if (sqlDataType.equals(SqlDataType.VARCHAR)) {
                length = ColumnDefaults.getDefaultVarcharLength();
            }
            Boolean primaryKey = ColumnDefaults.getDefaultPrimaryKey(field.getName());
            Boolean notNull = ColumnDefaults.getDefaultNotNull();
            Table table = new Table(clazz.getSimpleName());
            column = new Column(columnName, sqlDataType, length, primaryKey, notNull, table);
            columns.add(column);
        }
        return columns;
    }

    public Column readPhysicalColumn() {
        return null;
    }

    public Boolean physicalColumnExists(Column column) {
        return false;
    }

    public Boolean physicalColumnsExist(List<Cell> cells) {
        cells.stream().forEach((cell) -> {
            System.out.println(cell);
        });
        return false;
    }

    public void updatePhysicalColumn() {
        //UPDATE table1 SET col_a='k1', col_b='foo' WHERE key_col='1';
    }

    public void deletePhysicalColumn() {
        //ALTER TABLE table_name DROP COLUMN column_name
    }

    /**
     * Creates physical data in a table cell
     *
     * @param database database
     * @param tableName tableName
     * @param columnName columnName
     * @param value value
     * @param type type
     * @throws SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public void createPhysicalCell(Database_Deprecated database, String tableName, String columnName, Object value, Class type) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO" + " " + tableName + " " + "(" + columnName + ")" + " " + " VALUES" + " " + "(" + "?" + ")";
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter(1, value, type);
        parameters.add(parameter);
        database.preparedStatementUpdate(sql, parameters);
    }

    /**
     * Reads physical data from a table cell
     *
     * @param database database
     * @param tableName tableName
     * @param columnName columnName
     * @param value value
     * @param type type
     * @return Cell
     * @throws SQLException SQLException
     * @throws java.sql.SQLDataException SQLDataException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public Cell readPhysicalCell(Database_Deprecated database, String tableName, String columnName, Object value, Class type) throws SQLException, SQLDataException, ClassNotFoundException {
        String sql = "SELECT " + " " + columnName + "FROM" + " " + tableName + " " + "WHERE" + columnName + " " + "=" + " " + "?";
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter(1, value, type);
        parameters.add(parameter);
        ResultSet resultSet = database.preparedStatementSelect(sql, parameters);
        Cell cell = new Cell();
        while (resultSet.next()) {
            if (value instanceof String) {
                cell.setObject(resultSet.getString(columnName));
            } else if (value instanceof Integer) {
                cell.setObject(resultSet.getInt(columnName));
            } else if (value instanceof Object) {
                cell.setObject(resultSet.getBlob(columnName));
            } else if (value instanceof Date) {
                cell.setObject(resultSet.getDate(columnName));
            }
        }
        return cell;
    }

    /**
     * Returns populated cell objects for a specific class based on its fields
     * and values
     *
     * @param record Record that will be read
     * @return Cell containing data in the physical cell
     * @throws java.lang.IllegalAccessException IllegalAccessException
     */
    public List<Cell> listVirtualCells(Record record) throws IllegalAccessException, IllegalArgumentException {
        if (!physicalRecordExists(record)) {
            return null;
        } else {
            Map<String, Object> fieldsAndValues = Reflections.objectToFieldsAndValuesMap(record.getTable(), Boolean.FALSE);
            List<Cell> cells = new ArrayList<>();
            fieldsAndValues.entrySet().stream().map((entry) -> {
                Cell cell = new Cell();
                cell.setRecord(record);
                cell.setColumn(new Column(entry.getKey(), sqlDataTypeMapper(entry.getValue().getClass().getSimpleName()), ColumnDefaults.getDefaultVarcharLength(), ColumnDefaults.getDefaultPrimaryKey(entry.getKey()), ColumnDefaults.getDefaultNotNull(), record.getTable()));
                cell.setObject(entry.getValue());
                return cell;
            }).forEach((cell) -> {
                cells.add(cell);
            });
            return cells;
        }
    }

    /**
     * Returns populated cell objects for a specific class based on its fields
     * and values
     *
     * @param clazz clazz
     * @return Cell containing data in the physical cell
     * @throws java.lang.IllegalAccessException IllegalAccessException
     * @throws java.lang.InstantiationException InstantiationException
     */
    public List<Cell> listVirtualCells(Class clazz) throws IllegalAccessException, IllegalArgumentException, InstantiationException {
        List<Field> fields = Reflections.classToFieldsList(clazz);
        List<Cell> cells = new ArrayList<>();
        fields.stream().map((field) -> {
            Cell cell = new Cell();
            cell.setRecord(null);
            cell.setColumn(new Column(field.getName(), sqlDataTypeMapper(field.getType().getSimpleName()), ColumnDefaults.getDefaultVarcharLength(), ColumnDefaults.getDefaultPrimaryKey(field.getName()), ColumnDefaults.getDefaultNotNull(), new Table(clazz.getSimpleName())));
            return cell;
        }).map((cell) -> {
            cell.setObject(null);
            return cell;
        }).forEach((cell) -> {
            cells.add(cell);
        });
        return cells;
    }

    /**
     * Updates physical data in a table cell
     *
     * @param tableName tableName
     * @param columnName columnName
     * @param currentValue currentValue
     * @param newValue newValue
     * @param currentType currentType
     * @param newType newType
     * @throws java.sql.SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public void updatePhysicalCell(String tableName, String columnName, Object currentValue, Object newValue, Class currentType, Class newType) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE" + " " + tableName + "SET" + " " + columnName + " " + "=" + " " + "?" + " " + "WHERE" + " " + columnName + " " + "=" + " " + "?";
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter1 = new Parameter(1, currentValue, currentType);
        Parameter parameter2 = new Parameter(2, newValue, newType);
        parameters.add(parameter1);
        parameters.add(parameter2);
        this.preparedStatementUpdate(sql, parameters);
    }

    /**
     * Deletes physical data from a table cell
     *
     * @param database database
     * @param tableName tableName
     * @param columnName columnName
     * @param value value
     * @param type type
     * @throws SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     */
    public void deletePhysicalCell(Database_Deprecated database, String tableName, String columnName, Object value, Class type) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE" + " " + tableName + "SET" + " " + columnName + " " + "=" + " " + "?" + " " + "WHERE" + " " + columnName + " " + "=" + " " + "?";
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter1 = new Parameter(1, null, null);
        Parameter parameter2 = new Parameter(2, value, type);
        parameters.add(parameter1);
        parameters.add(parameter2);
        database.preparedStatementUpdate(sql, parameters);
    }

    public void statement(final String sql) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        connect();
        try (Statement statement = this.connection.createStatement()) {
            statement.execute(sql);
        }
        disconnect();
    }

    //TODO Test this method
    public Object statement(final String sql, final Class clazz) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        Integer totalFetchedColumns;
        connect();
        try (Statement statement = this.connection.createStatement()) {
            resultSet = statement.executeQuery(sql);
            resultSetMetaData = resultSet.getMetaData();
            totalFetchedColumns = resultSetMetaData.getColumnCount();
        }
        String tableName = resultSetMetaData.getTableName(1);
        Table table = new Table(tableName);
        disconnect();
        Integer currentColumn = 0;
        List<Record> fetchedRecords = new ArrayList<>();
        List<Cell> tempCells = new ArrayList<>();
        Record tempRecord = (Record) clazz.newInstance();
        tempRecord.setTable(table);
        while (resultSet.next()) {
            currentColumn++;
            if (Objects.equals(currentColumn, totalFetchedColumns)) {
                currentColumn = 0;
                tempRecord.setCells(tempCells);
                fetchedRecords.add(tempRecord);
                tempRecord = (Record) clazz.newInstance();
                tempRecord.setTable(table);
                tempCells = new ArrayList<>();
            }
            String columnName = resultSetMetaData.getColumnName(currentColumn);
            SqlDataType sqlDataType = sqlDataTypeMapper(resultSetMetaData.getColumnClassName(currentColumn));
            Integer columnLength = resultSetMetaData.getPrecision(currentColumn);
            Boolean primaryKey = null;
            Boolean notNull = null;
            Column column = new Column(columnName, sqlDataType, columnLength, primaryKey, notNull, table);

            Cell cell = new Cell();
            cell.setRecord(tempRecord);
            cell.setColumn(column);
            cell.setObject(resultSet.getObject(currentColumn));
            tempCells.add(cell);
        }
        return fetchedRecords;
    }

//    public List<Object> convertAll(List<Record> records, Class clazz) throws IllegalAccessException, InstantiationException {
//        List<Object> objects = new ArrayList<>();
//        for (Record record : records) {
//            Object object = clazz.newInstance();
//
//        }
//        return objects;
//    }
    /**
     *
     * @param tableName name of the table to perform a count query on
     * @param conditions i.e. "color = blue", "fruit = apple"
     * @return Integer count
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Integer count(final String tableName, final String... conditions) throws ClassNotFoundException, SQLException {
        connect();
        ResultSet resultSet;
        Integer count;
        try (Statement statement = this.connection.createStatement()) {
            String countStatement = "SELECT COUNT(*) FROM " + tableName;
            String whereStatement = " WHERE ";
            List<String> whereConditions = Arrays.asList(conditions);
            for (String condition : whereConditions) {
                if (whereStatement.equals("WHERE ")) {
                    whereStatement += condition;
                } else {
                    whereStatement += " AND " + condition;
                }
            }
            resultSet = statement.executeQuery(countStatement + whereStatement);
        }
        count = resultSet.getInt(1);
        resultSet.close();
        disconnect();
        return count;
    }

    public Integer preparedStatementUpdate(String sql, List<Parameter> parameters) throws SQLDataException, SQLException, ClassNotFoundException {
        Integer totalPlaceholders = StringUtils.countMatches(sql, "?");
        if (totalPlaceholders != parameters.size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connect();
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        for (Parameter parameter : parameters) {
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
            }
        }
        Integer rowCount = preparedStatement.executeUpdate();
        preparedStatement.closeOnCompletion();
        disconnect();
        return rowCount;
    }

    public ResultSet preparedStatementSelect(String sql, List<Parameter> parameters) throws SQLDataException, SQLException, ClassNotFoundException {
        Integer totalPlaceholders = StringUtils.countMatches(sql, "?");
        if (totalPlaceholders != parameters.size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connect();
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        for (Parameter parameter : parameters) {
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
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.closeOnCompletion();
        disconnect();
        return resultSet;
    }

    /**
     * List of all available SQL data types
     */
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

    public SqlDataType sqlDataTypeMapper(String type) {
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

    public SqlDataType sqlDataMapper(Class clazz) {
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

    public Class javaDataMapper(SqlDataType sqlDataType) {
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

    public static final class ColumnDefaults {

        public static Boolean getDefaultNotNull() {
            return false;
        }

        public static Boolean getDefaultPrimaryKey(String fieldName) {
            return fieldName.equalsIgnoreCase("id");
        }

        public static Integer getDefaultVarcharLength() {
            return 30;
        }

    }

    public class Parameter {

        private Integer placeholderIndex;
        private Object value;
        private Class type;

        public Parameter() {

        }

        public Parameter(Integer placeholderIndex, Object value, Class type) {
            this.placeholderIndex = placeholderIndex;
            this.value = value;
            this.type = type;
        }

        public Integer getPlaceholderIndex() {
            return placeholderIndex;
        }

        public void setPlaceholderIndex(Integer placeholderIndex) {
            this.placeholderIndex = placeholderIndex;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Parameter{" + "placeholderIndex=" + placeholderIndex + ", value=" + value + ", type=" + type + '}';
        }

    }

    @Override
    public String toString() {
        return "Database{" + "name=" + name + ", destination=" + destination + ", tables=" + tables + ", connection=" + connection + '}';
    }

}
