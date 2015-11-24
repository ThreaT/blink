package cool.blink.back.database;

import cool.blink.back.cluster.Action;
import cool.blink.back.utilities.Logs;
import cool.blink.back.utilities.Logs.Priority;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * API
 *
 * <ul>
 * <li>connect()</li>
 * <li>disconnect()</li>
 * </ul>
 *
 * <h3>Create</h3>
 * <br/>
 * <ul>
 * <li>public void createCell - Will not be implemented</li>
 * <li>public void createRecord(Object object)</li>
 * <li>public void createColumn - Will not be implemented</li>
 * <li>public void createTable(Class clazz)</li>
 * <li>public void createDatabase()</li>
 * </ul>
 *
 * <h3>Read</h3>
 * <br/>
 * <ul>
 * <li>public List&gt;Cell&lt; readCells - Will not be implemented</li>
 * <li>public List&gt;Record&lt; readRecords(Class clazz, String
 * whereClause)</li>
 * <li>public Column readColumn - Will not be implemented</li>
 * <li>public Table readTable(Class clazz)</li>
 * <li>public Boolean tableExists(Table table)</li>
 * <li>public Database readDatabase - Will not be implemented</li>
 * <li>public Boolean databaseExists()</li>
 * </ul>
 *
 * <h3>Update</h3>
 * <br/>
 * <ul>
 * <li>public void updateCell - Will not be implemented</li>
 * <li>public void updateRecord(Object oldRecord, Object newRecord)</li>
 * <li>public void updateColumn - Will not be implemented</li>
 * <li>public void updateTable - Will not be implemented</li>
 * <li>public void updateDatabase()</li>
 * </ul>
 *
 * <h3>Delete</h3>
 * <br/>
 * <ul>
 * <li>public void deleteCell - Will not be implemented</li>
 * <li>public void deleteRecord(Object object)</li>
 * <li>public void deleteColumn - Will not be implemented</li>
 * <li>public void deleteTable(Class clazz)</li>
 * <li>public void deleteDatabase()</li>
 * </ul>
 */
public final class Database {

    private String name;
    private String destination;
    private List<Class> tables;
    private Connection connection;

    public Database(final String name, final String destination) {
        this.name = name.toLowerCase();
        this.destination = destination;
        this.tables = new ArrayList<>();

        //Add action table
        this.tables.add(Action.class);

        try {
            createDatabase();
            for (Class clazz : this.tables) {
                createTable(clazz);
            }
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

    public void createRecord(Object object) {

    }

    public void createTable(Class clazz) throws ClassNotFoundException, SQLException {
        Table table = new Table(clazz.getSimpleName());
        List<Column> columns = this.listVirtualColumns(clazz);
        table.setColumns(columns);
        if (tableExists(table)) {
            Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.HIGH, "{0} table already exists.", clazz.getSimpleName());
        } else {
            this.createPhysicalTable(table);
            if (tableExists(table)) {
                Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.MEDIUM, "{0} table has been created.", clazz.getSimpleName());
            } else {
                Logger.getLogger(Database_Deprecated.class.getName()).log(Priority.HIGH, "There was a problem while attempting to create a physical table titled {0}.", clazz.getSimpleName());
            }
        }
    }

    public void createDatabase() throws SQLException {
        if (databaseExists()) {
            Logger.getLogger(Database.class.getName()).log(Logs.Priority.HIGH, "Database already exists in {0}/{1}, this database will be used.", new Object[]{this.getDestination(), this.getName()});
        } else {
            connection = DriverManager.getConnection("jdbc:derby:" + this.getDestination() + "/" + this.getName() + ";" + "create=true");
            disconnect();
            if (databaseExists()) {
                Logger.getLogger(Database.class.getName()).log(Logs.Priority.MEDIUM, "Database created in {0}/{1}", new Object[]{this.getDestination(), this.getName()});
            } else {
                Logger.getLogger(Database.class.getName()).log(Logs.Priority.HIGH, "There was a problem while attempting to create the physical database.");
            }
        }
    }

    public List<Record> readRecords(Class clazz, String whereClause) {
        return null;
    }

    public Table readTable(Class clazz) {
        return null;
    }

    public Boolean tableExists(Table table) throws SQLException, ClassNotFoundException {
        connect();
        DatabaseMetaData databaseMetaData = this.connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, table.getName().toUpperCase(), null);
        Boolean exists = resultSet.next();
        disconnect();
        return exists;
    }

    public Boolean databaseExists() {
        File databaseFile = new File(this.getDestination() + "/" + this.getName());
        return databaseFile.exists();
    }

}
