package cool.blink.back.database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String args[]) {
        Database database = new Database("TestDB", "", TestClass.class);
//        TestClass tc = new TestClass();
//        try {
//            database.createRecord(tc);
//        } catch (ClassNotFoundException | SQLException | IllegalAccessException | IllegalArgumentException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}


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
 * <li>public final void addTransaction(final String preparedSql)</li>
 * <li>public final void executeAll()</li>
 * <li>public final Object recordToObject(Record record)</li>
 * <li>public final List&gt;Object&lt; recordsToObjects(final List&gt;Record&lt; records, List&gt;Object&lt; objects)</li>
 * </ul>
 *
 * <h3>Create</h3>
 * <br/>
 * <ul>
 * <li>public final void createCell - Will not be implemented</li>
 * <li>public final void createRecord(final Object object)</li>
 * <li>public final void createColumn - Will not be implemented</li>
 * <li>public final void createTable(final Class clazz)</li>
 * <li>public final void createDatabase()</li>
 * </ul>
 *
 * <h3>Read</h3>
 * <br/>
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
 * <br/>
 * <ul>
 * <li>public final void updateCell - Will not be implemented</li>
 * <li>public final void updateRecord - Will not be implemented</li>
 * <li>public final void updateColumn - Will not be implemented</li>
 * <li>public final void updateTable - Will not be implemented</li>
 * <li>public final void updateDatabase - Will not be implemented</li>
 * </ul>
 *
 * <h3>Delete</h3>
 * <br/>
 * <ul>
 * <li>public final void deleteCell - Will not be implemented</li>
 * <li>public final void deleteRecord - Will not be implemented</li>
 * <li>public final void deleteColumn - Will not be implemented</li>
 * <li>public final void deleteTable(final Class clazz)</li>
 * <li>public final void deleteDatabase()</li>
 * </ul>
 */
