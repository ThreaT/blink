package cool.blink.back.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String args[]) {
        Database database = new Database("TestDB", "", TestClass.class);
        TestClass tc = new TestClass();
        try {
            database.createRecord(tc);
            System.out.println(database.readRecords(TestClass.class, "SELECT * FROM TESTCLASS").toString());
        } catch (SQLException | IllegalArgumentException | ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/**
 * API
 *
 * <h3>Misc</h3>
 * <ul>
 *
 * <li>public final void unsafeExecute(final String sql)</li>
 * <li>public final void execute(final String preparedSql, final Parameter...
 * parameters)</li>
 * <li>public final void addTransaction(final String preparedSql)</li>
 * <li>public final void executeAll()</li>
 * </ul>
 *
 *
 *
 * <h3>Read</h3>
 * <br/>
 * <ul>
 * <li>public final List&gt;Record&lt; readRecords(final Class clazz, final
 * String whereClause)</li>
 *
 *
 * </ul>
 *
 */
