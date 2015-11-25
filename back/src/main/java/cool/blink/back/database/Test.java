package cool.blink.back.database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String args[]) {
        Database database = new Database("TestDB", "", TestClass.class);
        TestClass tc = new TestClass();
        try {
            database.createRecord(tc);
        } catch (ClassNotFoundException | SQLException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
