package cool.blink.back.database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String args[]) {
        Database database = new Database("TestDB", "", TestClass.class);
        try {
            database.unsafeExecute("truncate table TESTCLASS");
            database.addTransaction(new PreparedEntry("INSERT INTO TESTCLASS(name) VALUES(?)", new Parameter(1, "helloworld10", String.class)));
            database.addTransaction(new PreparedEntry("INSERT INTO TESTCLASS(name) VALUES(?)", new Parameter(1, "helloworld100", String.class)));
            database.addTransaction(new PreparedEntry("INSERT INTO TESTCLASS(name) VALUES(?)", new Parameter(1, "helloworld1000", String.class)));
            database.addTransaction(new PreparedEntry("INSERT INTO TESTCLASS(name) VALUES(?)", new Parameter(1, "helloworld10000", String.class)));
            database.addTransaction(new PreparedEntry("INSERT INTO TESTCLASS(name) VALUES(?)", new Parameter(1, "helloworld100000", String.class)));
            database.executeAll();
        } catch (ClassNotFoundException | SQLException ex) {
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
 * <li>public final void addTransaction(final String preparedSql)</li>
 * <li>public final void executeAll()</li>
 * </ul>
 *
 *
 */
