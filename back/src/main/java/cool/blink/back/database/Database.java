package cool.blink.back.database;

import cool.blink.back.database.Column.BIGINT;
import cool.blink.back.database.Column.BLOB;
import cool.blink.back.database.Column.BOOLEAN;
import cool.blink.back.database.Column.CHAR;
import cool.blink.back.database.Column.CHAR_FOR_BIT_DATA;
import cool.blink.back.database.Column.CLOB;
import cool.blink.back.database.Column.ColumnGenerationType;
import cool.blink.back.database.Column.DATE;
import cool.blink.back.database.Column.DECIMAL;
import cool.blink.back.database.Column.DOUBLE;
import cool.blink.back.database.Column.DOUBLE_PRECISION;
import cool.blink.back.database.Column.FLOAT;
import cool.blink.back.database.Column.INTEGER;
import cool.blink.back.database.Column.LONG_VARCHAR;
import cool.blink.back.database.Column.LONG_VARCHAR_FOR_BIT_DATA;
import cool.blink.back.database.Column.NUMERIC;
import cool.blink.back.database.Column.REAL;
import cool.blink.back.database.Column.SMALLINT;
import cool.blink.back.database.Column.TIME;
import cool.blink.back.database.Column.TIMESTAMP;
import cool.blink.back.database.Column.VARCHAR;
import cool.blink.back.database.Column.VARCHAR_FOR_BIT_DATA;
import cool.blink.back.exception.InvalidActionParameterException;
import cool.blink.back.utilities.DateUtilities;
import cool.blink.back.utilities.LogUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.back.utilities.ReflectionUtilities;
import cool.blink.back.utilities.StringUtilities;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import net.sf.log4jdbc.PreparedStatementSpy;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

//TODO refresh this list of methods
//TODO Blink actions do not save byte objects across the cluster properly in action insert/rollback statements
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
 * <li>public final Class javaDataMapper(final SqlDataType columnType)</li>
 * <li>public final void unsafeExecute(final String sql)</li>
 * <li>public final void execute(final String preparedSql, final Parameter...
 * parameters)</li>
 * <li>public final synchronized void addUnsafeTransaction(final String
 * query)</li>
 * <li>public final synchronized void executeUnsafeBatch()</li>
 * <li>public final void execute(final Environment environment, final String
 * driverName, final String dump)</li>
 * </ul>
 *
 * <h3>Create</h3>
 * <br>
 * <ul>
 * <li>public final void createCell - Will not be implemented</li>
 * <li>public final void createClusteredRecord(final Object object)</li>
 * <li>public final void createColumn - Will not be implemented</li>
 * <li>public final void createTable(final Class clazz)</li>
 * <li>public final void createTable(final Column... columns)</li>
 * <li>public final void createDatabase()</li>
 * </ul>
 *
 * <h3>Read</h3>
 * <br>
 * <ul>
 * <li>public final List&gt;Cell&lt; readCells - Will not be implemented</li>
 * <li>public final List&gt;Record&lt; readRecordsForBlink(final Class clazz,
 * final String whereClause)</li>
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

    private final DatabaseDetails databaseDetails;
    private final DatabaseChain databaseChain;
    private final DatabaseBackup databaseBackup;
    private final DatabaseSocketManager socketManager;
    private final DatabasePortScanner databasePortScanner;
    private final ActionSynchronizer actionSynchronizer;
    private final ActionExecutor actionExecutor;
    private final Builder builder;
    private Connection connection;
    private Connection altConnection;

    public Database(final DatabaseDetails databaseDetails, final DatabaseChain databaseChain, final DatabaseBackup databaseBackup, final DatabaseSocketManager socketManager, final DatabasePortScanner databasePortScanner, final ActionSynchronizer actionSynchronizer, final ActionExecutor actionExecutor, final Builder builder) {
        this.databaseDetails = databaseDetails;
        this.databaseChain = databaseChain;

        this.databaseBackup = databaseBackup;
        this.databaseBackup.setDatabaseChain(databaseChain);

        this.socketManager = socketManager;
        this.socketManager.setDatabaseChain(databaseChain);

        this.databasePortScanner = databasePortScanner;
        this.databasePortScanner.setDatabaseChain(databaseChain);

        this.actionSynchronizer = actionSynchronizer;
        this.actionSynchronizer.setDatabaseChain(databaseChain);

        this.actionExecutor = actionExecutor;
        this.actionExecutor.setDatabaseChain(databaseChain);

        this.builder = builder;
    }

    public void begin() {
        try {
            //Initialize database
            this.builder.execute();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        }
        this.databaseBackup.start();
        Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "Backup Started.");
        this.socketManager.start();
        Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "Socket Manager Started.");
        this.databasePortScanner.start();
        Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "Port Scanner Started.");
        this.actionSynchronizer.start();
        Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "Action Synchronizer Started.");
        this.actionExecutor.start();
        Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "Action Executor Started.");
    }

    public DatabaseDetails getDatabaseDetails() {
        return databaseDetails;
    }

    public DatabaseChain getDatabaseChain() {
        return databaseChain;
    }

    public DatabaseBackup getDatabaseBackup() {
        return databaseBackup;
    }

    public DatabaseSocketManager getSocketManager() {
        return socketManager;
    }

    public DatabasePortScanner getDatabasePortScanner() {
        return databasePortScanner;
    }

    public ActionSynchronizer getActionSynchronizer() {
        return actionSynchronizer;
    }

    public ActionExecutor getActionExecutor() {
        return actionExecutor;
    }

    public Builder getBuilder() {
        return builder;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getAltConnection() {
        return altConnection;
    }

    public void setAltConnection(Connection altConnection) {
        this.altConnection = altConnection;
    }

    @SuppressWarnings({"SleepWhileInLoop", "SleepWhileHoldingLock"})
    public final void connect() throws ClassNotFoundException, SQLException {
        final Integer maxAttempts = 10;
        Integer totalAttempts = 0;
        while (this.connection != null) {
            totalAttempts++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
            }
            if (Objects.equals(totalAttempts, maxAttempts)) {
                Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "Cannot get database connection, attempting to kill current connection.");
                disconnect();
                break;
            }
        }
        Class.forName("net.sf.log4jdbc.DriverSpy");
        Connection temp = DriverManager.getConnection("jdbc:log4jdbc:derby:" + new File(this.databaseChain.getDatabaseDestination() + this.databaseChain.getDatabaseName()).getAbsolutePath());
        this.connection = new net.sf.log4jdbc.ConnectionSpy(temp);
        this.connection.setAutoCommit(false);
    }

    @SuppressWarnings({"SleepWhileInLoop", "SleepWhileHoldingLock"})
    public final void disconnect() throws SQLException {
        final Integer maxAttempts = 10;
        Integer totalAttempts = 0;
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                try {
                    this.connection.commit();
                    this.connection.close();
                } catch (SQLException ex1) {
                    while (this.connection != null) {
                        totalAttempts++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex2) {
                            Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex2);
                        }
                        if (Objects.equals(totalAttempts, maxAttempts)) {
                            Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "Cannot get database connection, performing rollback and killing current connection.");
                            this.connection.rollback();
                            this.connection.close();
                            break;
                        }
                    }
                }
            }
            this.connection = null;
        }
    }

    @SuppressWarnings({"SleepWhileInLoop", "SleepWhileHoldingLock", "SynchronizeOnNonFinalField"})
    public final void connectForBlink() throws ClassNotFoundException, SQLException {
        final Integer maxAttempts = 10;
        Integer totalAttempts = 0;
        while (this.altConnection != null) {
            totalAttempts++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
            }
            if (Objects.equals(totalAttempts, maxAttempts)) {
                Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "Cannot get database connection, attempting to kill current connection.");
                disconnectForBlink();
                break;
            }
        }
        Class.forName("net.sf.log4jdbc.DriverSpy");
        Connection temp = DriverManager.getConnection("jdbc:log4jdbc:derby:" + new File(this.databaseChain.getDatabaseDestination() + this.databaseChain.getDatabaseName()).getAbsolutePath());
        this.altConnection = new net.sf.log4jdbc.ConnectionSpy(temp);
        this.altConnection.setAutoCommit(false);
    }

    @SuppressWarnings({"SynchronizeOnNonFinalField", "SleepWhileInLoop", "SleepWhileHoldingLock"})
    public final void disconnectForBlink() throws SQLException {
        final Integer maxAttempts = 10;
        Integer totalAttempts = 0;
        if (this.altConnection != null) {
            try {
                this.altConnection.close();
            } catch (SQLException ex) {
                try {
                    this.altConnection.commit();
                    this.altConnection.close();
                } catch (SQLException ex1) {
                    while (this.altConnection != null) {
                        totalAttempts++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex2) {
                            Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex2);
                        }
                        if (Objects.equals(totalAttempts, maxAttempts)) {
                            Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "Cannot get database connection, performing rollback and killing current connection.");
                            this.altConnection.rollback();
                            this.altConnection.close();
                            break;
                        }
                    }
                }
            }
            this.altConnection = null;
        }
    }

    private String getTypeName(final Class derbyType) {
        String typeName = null;
        if (derbyType.equals(BIGINT.class)) {
            typeName = BIGINT.class.getSimpleName();
        } else if (derbyType.equals(BLOB.class)) {
            typeName = BLOB.class.getSimpleName();
        } else if (derbyType.equals(BOOLEAN.class)) {
            typeName = BOOLEAN.class.getSimpleName();
        } else if (derbyType.equals(CHAR.class)) {
            typeName = CHAR.class.getSimpleName();
        } else if (derbyType.equals(CHAR_FOR_BIT_DATA.class)) {
            typeName = "CHAR FOR BIT DATA";
        } else if (derbyType.equals(CLOB.class)) {
            typeName = CLOB.class.getSimpleName();
        } else if (derbyType.equals(DATE.class)) {
            typeName = DATE.class.getSimpleName();
        } else if (derbyType.equals(DECIMAL.class)) {
            typeName = DECIMAL.class.getSimpleName();
        } else if (derbyType.equals(DOUBLE.class)) {
            typeName = DOUBLE.class.getSimpleName();
        } else if (derbyType.equals(DOUBLE_PRECISION.class)) {
            typeName = "DOUBLE PRECISION";
        } else if (derbyType.equals(FLOAT.class)) {
            typeName = FLOAT.class.getSimpleName();
        } else if (derbyType.equals(INTEGER.class)) {
            typeName = INTEGER.class.getSimpleName();
        } else if (derbyType.equals(LONG_VARCHAR.class)) {
            typeName = "LONG VARCHAR";
        } else if (derbyType.equals(LONG_VARCHAR_FOR_BIT_DATA.class)) {
            typeName = "LONG VARCHAR FOR BIT DATA";
        } else if (derbyType.equals(NUMERIC.class)) {
            typeName = NUMERIC.class.getSimpleName();
        } else if (derbyType.equals(REAL.class)) {
            typeName = REAL.class.getSimpleName();
        } else if (derbyType.equals(SMALLINT.class)) {
            typeName = SMALLINT.class.getSimpleName();
        } else if (derbyType.equals(TIME.class)) {
            typeName = TIME.class.getSimpleName();
        } else if (derbyType.equals(TIMESTAMP.class)) {
            typeName = TIMESTAMP.class.getSimpleName();
        } else if (derbyType.equals(VARCHAR.class)) {
            typeName = VARCHAR.class.getSimpleName();
        } else if (derbyType.equals(VARCHAR_FOR_BIT_DATA.class)) {
            typeName = "VARCHAR FOR BIT DATA";
        }
        return typeName;
    }

    private Class getDerbyType(final String typeName) {
        Class derbyType = null;
        if (typeName.equalsIgnoreCase("BIGINT")) {
            derbyType = BIGINT.class;
        } else if (typeName.equalsIgnoreCase("BLOB")) {
            derbyType = BLOB.class;
        } else if (typeName.equalsIgnoreCase("BOOLEAN")) {
            derbyType = BOOLEAN.class;
        } else if (typeName.equalsIgnoreCase("CHAR")) {
            derbyType = CHAR.class;
        } else if (typeName.equalsIgnoreCase("CHAR FOR BIT DATA")) {
            derbyType = CHAR_FOR_BIT_DATA.class;
        } else if (typeName.equalsIgnoreCase("CLOB")) {
            derbyType = CLOB.class;
        } else if (typeName.equalsIgnoreCase("DATE")) {
            derbyType = DATE.class;
        } else if (typeName.equalsIgnoreCase("DECIMAL")) {
            derbyType = DECIMAL.class;
        } else if (typeName.equalsIgnoreCase("DOUBLE")) {
            derbyType = DOUBLE.class;
        } else if (typeName.equalsIgnoreCase("DOUBLE PRECISION")) {
            derbyType = DOUBLE_PRECISION.class;
        } else if (typeName.equalsIgnoreCase("FLOAT")) {
            derbyType = FLOAT.class;
        } else if (typeName.equalsIgnoreCase("INTEGER")) {
            derbyType = INTEGER.class;
        } else if (typeName.equalsIgnoreCase("LONG VARCHAR")) {
            derbyType = LONG_VARCHAR.class;
        } else if (typeName.equalsIgnoreCase("LONG VARCHAR FOR BIT DATA")) {
            derbyType = LONG_VARCHAR_FOR_BIT_DATA.class;
        } else if (typeName.equalsIgnoreCase("NUMERIC")) {
            derbyType = NUMERIC.class;
        } else if (typeName.equalsIgnoreCase("REAL")) {
            derbyType = REAL.class;
        } else if (typeName.equalsIgnoreCase("SMALLINT")) {
            derbyType = SMALLINT.class;
        } else if (typeName.equalsIgnoreCase("TIME")) {
            derbyType = TIME.class;
        } else if (typeName.equalsIgnoreCase("TIMESTAMP")) {
            derbyType = TIMESTAMP.class;
        } else if (typeName.equalsIgnoreCase("VARCHAR")) {
            derbyType = VARCHAR.class;
        } else if (typeName.equalsIgnoreCase("VARCHAR FOR BIT DATA")) {
            derbyType = VARCHAR_FOR_BIT_DATA.class;
        }
        return derbyType;
    }

    private List<Column> generateColumns(final ResultSet resultSet, final Table table) throws ClassNotFoundException, SQLException {
        List<Column> columns = new ArrayList<>();
        ResultSetMetaData columnMeta = resultSet.getMetaData();
        Integer columnCount = columnMeta.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            String columnName = columnMeta.getColumnName(i + 1);
            Class derbyType = getDerbyType(columnMeta.getColumnTypeName(i + 1).toUpperCase());
            Integer columnLength = columnMeta.getPrecision(i + 1);
            Boolean columnPrimaryKey = columnMeta.isAutoIncrement(i + 1);
            Boolean columnNotNull = columnMeta.isNullable(i + 1) == 0;
            Column column = generateColumn(columnName, derbyType, columnLength, columnPrimaryKey, columnNotNull, table);
            columns.add(column);

        }
        return columns;
    }

    private Column generateColumn(final String columnName, final Class derbyType, final Integer columnLength, final Boolean columnPrimaryKey, final Boolean columnNotNull, final Table table) {
        Column column = null;
        if (derbyType.equals(BIGINT.class)) {
            column = new BIGINT(columnName, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(BLOB.class)) {
            column = new BLOB(columnName, columnLength, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(BOOLEAN.class)) {
            column = new BOOLEAN(columnName, columnNotNull, table);
        } else if (derbyType.equals(CHAR.class)) {
            column = new CHAR(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(CLOB.class)) {
            column = new CLOB(columnName, columnLength, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(DATE.class)) {
            column = new DATE(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(BIGINT.class)) {
            column = new BIGINT(columnName, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(DECIMAL.class)) {
            column = new DECIMAL(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(DOUBLE.class)) {
            column = new DOUBLE(columnName, columnNotNull, table);
        } else if (derbyType.equals(FLOAT.class)) {
            column = new FLOAT(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(INTEGER.class)) {
            if (columnPrimaryKey) {
                column = new INTEGER(columnName, ColumnGenerationType.BY_DEFAULT, columnPrimaryKey, columnNotNull, table);
            } else {
                column = new INTEGER(columnName, columnNotNull, table);
            }
        } else if (derbyType.equals(NUMERIC.class)) {
            column = new NUMERIC(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(REAL.class)) {
            column = new REAL(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(SMALLINT.class)) {
            column = new SMALLINT(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(TIME.class)) {
            column = new TIME(columnName, null, columnLength, null, columnPrimaryKey, columnNotNull, table);
        } else if (derbyType.equals(TIMESTAMP.class)) {
            column = new TIMESTAMP(columnName, columnNotNull, table);
        } else if (derbyType.equals(VARCHAR.class)) {
            column = new VARCHAR(columnName, columnLength, columnPrimaryKey, columnNotNull, table);
        }
        return column;
    }

    public final void unsafeExecute(final String sql) throws ClassNotFoundException, SQLException {
        connect();
        try (Statement statement = this.connection.createStatement()) {
            statement.execute(sql);
        }
        disconnect();
    }

    public final void unsafeExecuteForBlink(final String sql) throws ClassNotFoundException, SQLException {
        connectForBlink();
        try (Statement statement = this.altConnection.createStatement()) {
            statement.execute(sql);
        }
        disconnectForBlink();
    }

    @SuppressWarnings("ConvertToTryWithResources")
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
        preparedStatement.close();
        disconnect();
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public final synchronized void executeForBlink(final PreparedEntry preparedEntry) throws ClassNotFoundException, SQLException {
        Integer totalPlaceholders = StringUtils.countMatches(preparedEntry.getSql(), "?");
        if (totalPlaceholders != preparedEntry.getParameters().size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connectForBlink();
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
        preparedStatement.close();
        disconnectForBlink();
    }

    public final void createRecord(final Object object) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SQLException {
        //Generate reflection data from object
        List<Field> fields = ReflectionUtilities.classToFieldsList(object.getClass());
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 1; i < fields.size() + 1; i++) {
            Integer placeholderIndex = parameters.size() + 1;
            Object value = PropertyUtils.getProperty(object, fields.get(i - 1).getName());
            Class type = fields.get(i - 1).getType();
            Parameter parameter = new Parameter(placeholderIndex, value, type);
            parameters.add(parameter);
        }

        //Create forward statement
        String forward = "INSERT INTO " + object.getClass().getSimpleName().toUpperCase() + "(";
        for (int i = 1; i < fields.size() + 1; i++) {
            fields.get(i - 1).setAccessible(true);
            forward += fields.get(i - 1).getName();
            if (fields.size() > i) {
                forward += ",";
            }
        }
        forward += ") VALUES(";
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            Parameter parameter = parameters.get(i);
            switch (parameter.getType().getSimpleName().toLowerCase()) {
                case "string":
                    forward += "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
                case "integer":
                    forward += StringUtilities.escapeDerby(fields.get(i).get(object).toString());
                    break;
                case "date":
                    forward += "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
                case "long":
                    forward += StringUtilities.escapeDerby(fields.get(i).get(object).toString());
                    break;
                default:
                    forward += "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
            }
            if (fields.size() > i + 1) {
                forward += ",";
            } else {
                forward += ")";
            }
        }

        unsafeExecute(forward);
    }

    /**
     * This method must be executed after a successful database write event in
     * order to sync the data across all other databases in the cluster
     *
     * @param databaseId databaseId
     * @param forwardStatement forwardStatement
     * @param rollbackStatement rollbackStatement
     * @param generateKeys ResultSet is returned with ID of newly inserted
     * record and is obtainable using resultSet.getInt(1)
     * @return PreparedStatement forwardStatement after execution is complete
     * @throws java.lang.NoSuchMethodException NoSuchMethodException
     * @throws java.sql.SQLException SQLException
     * @throws java.lang.ClassNotFoundException ClassNotFoundException
     * @throws java.lang.IllegalAccessException IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * InvocationTargetException
     */
    public final Clusterable addToCluster(final String databaseId, final PreparedStatement forwardStatement, final PreparedStatement rollbackStatement, final Boolean generateKeys) throws NoSuchMethodException, SQLException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //Run once to ensure that forwardStatement works as expected
        String forwardQuery = "";
        if (forwardStatement instanceof PreparedStatementSpy) {
            Method m = PreparedStatementSpy.class.getDeclaredMethod("dumpedSql");
            m.setAccessible(true);
            forwardQuery = (String) m.invoke(forwardStatement);
        }

        //Run once to ensure that rollbackStatement works as expected
        String rollBackQuery = "";
        if (rollbackStatement instanceof PreparedStatementSpy) {
            Method m = PreparedStatementSpy.class.getDeclaredMethod("dumpedSql");
            m.setAccessible(true);
            rollBackQuery = (String) m.invoke(forwardStatement);
        }

        //Run second time to perform the database action for real
        forwardStatement.executeUpdate();

        //Get ID of newly inserted record
        ResultSet generatedKeys = null;
        if (generateKeys) {
            generatedKeys = forwardStatement.getGeneratedKeys();
            generatedKeys.next();
        }

        //Insert action so that other databases can replicate the insert
        Long currentTime = System.currentTimeMillis();
        Action action = new Action(currentTime, databaseId, forwardQuery, rollBackQuery, currentTime);
        connectForBlink();
        PreparedStatement preparedStatement;
        preparedStatement = this.connection.prepareStatement("INSERT INTO ACTION (CREATIONTIME,DATABASEID,FORWARDSTATEMENT,ROLLBACKSTATEMENT,TIMEOFEXECUTION) VALUES(?,?,?,?,?)");
        preparedStatement.setLong(1, action.getCreationTime());
        preparedStatement.setString(2, action.getDatabaseId());
        preparedStatement.setString(3, action.getForwardStatement());
        preparedStatement.setString(4, action.getRollbackStatement());
        preparedStatement.setLong(5, action.getTimeOfExecution());
        preparedStatement.executeUpdate();
        disconnectForBlink();

        return new Clusterable(forwardStatement, rollbackStatement, generatedKeys);
    }

    private void addToClusterForBlink(final Action action) {
        try {
            connect();
            PreparedStatement preparedStatement;
            preparedStatement = this.connection.prepareStatement("INSERT INTO ACTION (CREATIONTIME,DATABASEID,FORWARDSTATEMENT,ROLLBACKSTATEMENT,TIMEOFEXECUTION) VALUES(?,?,?,?,?)");
            preparedStatement.setLong(1, action.getCreationTime());
            preparedStatement.setString(2, action.getDatabaseId());
            preparedStatement.setString(3, action.getForwardStatement());
            preparedStatement.setString(4, action.getRollbackStatement());
            Long timeOfExecution;
            if (action.getTimeOfExecution() == null) {
                timeOfExecution = System.currentTimeMillis();
            } else {
                timeOfExecution = action.getTimeOfExecution();
            }
            preparedStatement.setLong(5, timeOfExecution);
            preparedStatement.executeUpdate();
            disconnect();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

    public final void createClusteredRecord(final Object object) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SQLException {
        //Generate reflection data from object
        List<Field> fields = ReflectionUtilities.classToFieldsList(object.getClass());
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 1; i < fields.size() + 1; i++) {
            Integer placeholderIndex = parameters.size() + 1;
            Object value = PropertyUtils.getProperty(object, fields.get(i - 1).getName());
            Class type = fields.get(i - 1).getType();
            Parameter parameter = new Parameter(placeholderIndex, value, type);
            parameters.add(parameter);
        }

        //Create forward statement
        String forward = "INSERT INTO " + object.getClass().getSimpleName().toUpperCase() + "(";
        for (int i = 1; i < fields.size() + 1; i++) {
            fields.get(i - 1).setAccessible(true);
            forward += fields.get(i - 1).getName();
            if (fields.size() > i) {
                forward += ",";
            }
        }
        forward += ") VALUES(";
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            Parameter parameter = parameters.get(i);
            switch (parameter.getType().getSimpleName().toLowerCase()) {
                case "string":
                    forward += "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
                case "integer":
                    forward += StringUtilities.escapeDerby(fields.get(i).get(object).toString());
                    break;
                case "date":
                    forward += "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
                case "long":
                    forward += StringUtilities.escapeDerby(fields.get(i).get(object).toString());
                    break;
                default:
                    forward += "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
            }
            if (fields.size() > i + 1) {
                forward += ",";
            } else {
                forward += ")";
            }
        }

        //Create rollback statement
        String rollback = "DELETE FROM " + object.getClass().getSimpleName().toUpperCase() + " WHERE ";
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            Parameter parameter = parameters.get(i);
            switch (parameter.getType().getSimpleName().toLowerCase()) {
                case "string":
                    rollback += fields.get(i).getName() + " = " + "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
                case "integer":
                    rollback += fields.get(i).getName() + " = " + StringUtilities.escapeDerby(fields.get(i).get(object).toString());
                    break;
                case "date":
                    rollback += fields.get(i).getName() + " = " + "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
                case "long":
                    rollback += fields.get(i).getName() + " = " + StringUtilities.escapeDerby(fields.get(i).get(object).toString());
                    break;
                default:
                    rollback += fields.get(i).getName() + " = " + "'" + StringUtilities.escapeDerby(fields.get(i).get(object).toString()) + "'";
                    break;
            }
            if (fields.size() > i + 1) {
                rollback += " AND ";
            }
        }

        unsafeExecute(forward);

        try {
            addToClusterForBlink(new Action(System.currentTimeMillis(), this.getDatabaseDetails().getId(), forward, rollback));
        } catch (InvalidActionParameterException ex) {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        }

    }

    public final void createTable(final Class clazz, final Column... columns) throws ClassNotFoundException, SQLException {
        Table temp = new Table(clazz.getSimpleName());
        Table table = new Table(temp.getName(), temp.getSchema(), temp.getName(), temp.getType(), temp.getRemarks(), temp.getDatabase(), asList(columns), temp.getRecords());
        if (tableExists(table)) {
            Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "{0} table already exists.", clazz.getSimpleName());
        } else {
            //Create the table in the physical database
            String createStatement = "CREATE TABLE ";
            Column primaryKey = null;
            Class derbyType;
            String databaseName = "\"" + "APP" + "\"";
            String tableName = "\"" + table.getName() + "\"";
            createStatement += databaseName + "." + tableName;
            if (!table.getColumns().isEmpty()) {
                createStatement += " (";
            }
            for (int i = 0; i < table.getColumns().size(); i++) {
                createStatement += table.getColumns().get(i).getName();
                derbyType = table.getColumns().get(i).getClass();
                createStatement += " " + getTypeName(derbyType);
                if ((table.getColumns().get(i).getLength() != null) && (derbyType != BIGINT.class)) {
                    createStatement += " " + "(" + table.getColumns().get(i).getLength();
                    if (table.getColumns().get(i).getLengthSuffix() != null) {
                        createStatement += table.getColumns().get(i).getLengthSuffix();
                    }
                    createStatement += ")";
                }
                createStatement += (table.getColumns().get(i).getNotNull() == true ? " " + "NOT NULL" : "");
                if (table.getColumns().get(i).getPrimaryKey() == true) {
                    primaryKey = table.getColumns().get(i);
                    createStatement += " " + table.getColumns().get(i).getColumnGenerationType().toString();
                }
                if (i == table.getColumns().size() - 1) {
                    if (primaryKey != null) {
                        createStatement += ", PRIMARY KEY (" + primaryKey.getName() + ")";
                    }
                } else {
                    createStatement += ", ";
                }
            }
            if (!table.getColumns().isEmpty()) {
                createStatement += ")";
            }
            execute(new PreparedEntry(createStatement));
            if (tableExists(table)) {
                Logger.getLogger(Database.class.getName()).log(Priority.MEDIUM, "{0} table has been created.", clazz.getSimpleName());
            } else {
                Logger.getLogger(Database.class.getName()).log(Priority.HIGH, "There was a problem while attempting to create a physical table titled {0}.", clazz.getSimpleName());
            }
        }
    }

    public final void createDatabase() throws SQLException, IOException, ClassNotFoundException {
        if (databaseExists()) {
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGH, "Database already exists in {0}/{1}, this database will be used.", new Object[]{new File(this.databaseChain.getDatabaseDestination()).getAbsolutePath(), this.databaseChain.getDatabaseName()});
        } else {
            this.connection = DriverManager.getConnection("jdbc:derby:" + this.databaseChain.getDatabaseDestination() + this.databaseChain.getDatabaseName() + ";" + "create=true");
            disconnect();
            if (databaseExists()) {
                Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.MEDIUM, "Database created in {0}/{1}", new Object[]{this.databaseChain.getDatabaseDestination(), this.databaseChain.getDatabaseName()});
            } else {
                Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGH, "There was a problem while attempting to create the physical database.");
            }
            createTable(
                    Action.class,
                    new BIGINT("creationTime", ColumnGenerationType.BY_DEFAULT, true, true, new Table(Action.class.getSimpleName())),
                    new VARCHAR("databaseId", 100, true, new Table(Action.class.getSimpleName())),
                    new LONG_VARCHAR("forwardStatement", true, new Table(Action.class.getSimpleName())),
                    new LONG_VARCHAR("rollbackStatement", true, new Table(Action.class.getSimpleName())),
                    new BIGINT("timeOfExecution", true, new Table(Action.class.getSimpleName()))
            );
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
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
        List<Column> columns = generateColumns(resultSet, null);

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

        preparedStatement.close();
        disconnect();
        return records;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public final synchronized List<Record> readRecordsForBlink(final Class clazz, final PreparedEntry preparedEntry) throws ClassNotFoundException, SQLException, InterruptedException {
        Table table = new Table(clazz.getSimpleName());
        Integer totalPlaceholders = StringUtils.countMatches(preparedEntry.getSql(), "?");
        if (totalPlaceholders != preparedEntry.getParameters().size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connectForBlink();

        //Assign parameters to preparedSql
        PreparedStatement preparedStatement = this.altConnection.prepareStatement(preparedEntry.getSql());
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
                case "datetime":
                    preparedStatement.setTimestamp(parameter.getPlaceholderIndex(), DateUtilities.convert((DateTime) parameter.getValue()));
                    break;
                case "long":
                    preparedStatement.setLong(parameter.getPlaceholderIndex(), (Long) parameter.getValue());
                    break;
                default:
                    throw new SQLDataException("Invalid parameter type: " + parameter.toString());
            }
        }

        //Run query
        ResultSet resultSet = preparedStatement.executeQuery();

        //Get column data
        List<Column> columns = generateColumns(resultSet, table);

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
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Long) {
                    object = resultSet.getLong(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Date) {
                    object = resultSet.getDate(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Timestamp) {
                    object = resultSet.getTimestamp(resultSetMetaData.getColumnName(i + 1));
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
            records.remove(record);
            records.add(record);
        }
        preparedStatement.close();
        disconnectForBlink();
        return records;
    }

    @SuppressWarnings("ConvertToTryWithResources")
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
                case "datetime":
                    preparedStatement.setTimestamp(parameter.getPlaceholderIndex(), DateUtilities.convert((DateTime) parameter.getValue()));
                    break;
                case "long":
                    preparedStatement.setLong(parameter.getPlaceholderIndex(), (Long) parameter.getValue());
                    break;
                default:
                    throw new SQLDataException("Invalid parameter type: " + parameter.toString());
            }
        }

        //Run query
        ResultSet resultSet = preparedStatement.executeQuery();

        //Get column data
        List<Column> columns = generateColumns(resultSet, table);

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
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Long) {
                    object = resultSet.getLong(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Date) {
                    object = resultSet.getDate(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Timestamp) {
                    object = resultSet.getTimestamp(resultSetMetaData.getColumnName(i + 1));
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

        preparedStatement.close();
        disconnect();
        return records;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public final List<Record> readRecordsForBlink(final Class clazz, final PreparedEntry preparedEntry, final Integer limit) throws ClassNotFoundException, SQLException, InterruptedException {
        Table table = new Table(clazz.getSimpleName());
        Integer totalPlaceholders = StringUtils.countMatches(preparedEntry.getSql(), "?");
        if (totalPlaceholders != preparedEntry.getParameters().size()) {
            throw new SQLDataException("Incorrect number of parameters allocated for provided sql statement");
        }
        connectForBlink();

        //Assign parameters to preparedSql
        PreparedStatement preparedStatement = this.altConnection.prepareStatement(preparedEntry.getSql());
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
                case "datetime":
                    preparedStatement.setTimestamp(parameter.getPlaceholderIndex(), DateUtilities.convert((DateTime) parameter.getValue()));
                    break;
                case "long":
                    preparedStatement.setLong(parameter.getPlaceholderIndex(), (Long) parameter.getValue());
                    break;
                default:
                    throw new SQLDataException("Invalid parameter type: " + parameter.toString());
            }
        }

        //Run query
        preparedStatement.setMaxRows(limit);
        ResultSet resultSet = preparedStatement.executeQuery();

        //Get column data
        List<Column> columns = generateColumns(resultSet, table);

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
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Long) {
                    object = resultSet.getLong(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Date) {
                    object = resultSet.getDate(resultSetMetaData.getColumnName(i + 1));
                } else if (resultSet.getObject(resultSetMetaData.getColumnName(i + 1)) instanceof Timestamp) {
                    object = resultSet.getTimestamp(resultSetMetaData.getColumnName(i + 1));
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

        preparedStatement.close();
        disconnectForBlink();
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
                                Class derbyType = Class.forName(columnResultSet.getString("TYPE_NAME").toUpperCase());
                                Integer columnLength = columnResultSet.getInt("COLUMN_SIZE");
                                Boolean columnPrimaryKey = false;
                                columnPrimaryKeysSet = columnMeta.getPrimaryKeys(null, null, table.getName());
                                while (columnPrimaryKeysSet.next()) {
                                    if (columnPrimaryKeysSet.getString(4).equalsIgnoreCase(columnName)) {
                                        columnPrimaryKey = true;
                                    }
                                }
                                Boolean columnNotNull = columnResultSet.getString("NULLABLE").equals("0");
                                Column column = generateColumn(columnName, derbyType, columnLength, columnPrimaryKey, columnNotNull, table);
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

    public final synchronized Boolean tableExists(final Table table) throws SQLException, ClassNotFoundException {
        connect();
        DatabaseMetaData databaseMetaData = this.connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, "%", null);
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").equalsIgnoreCase(table.getName())) {
                disconnect();
                return true;
            }
        }
        disconnect();
        return false;
    }

    public final Boolean databaseExists() {
        File databaseFolder = new File(this.databaseChain.getDatabaseDestination() + this.databaseChain.getDatabaseName());
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
        File databaseLog = new File(this.databaseChain.getDatabaseDestination() + "derby.log");
        databaseLog.delete();
        File database = new File(this.databaseChain.getDatabaseDestination() + this.databaseChain.getDatabaseName());
        FileUtils.deleteDirectory(database);
    }

}
