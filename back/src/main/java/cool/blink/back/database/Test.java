package cool.blink.back.database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String args[]) {
        Database_Deprecated database = new Database_Deprecated("TestDB", "", TestClass.class);
        TestClass tc = new TestClass();
        try {
            database.createPhysicalRecord(tc);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
