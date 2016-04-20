package cool.blink.examples.helloworld2;

import cool.blink.back.core.Container;
import cool.blink.back.database.Builder;
import cool.blink.back.database.Database;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.examples.helloworld2.table.Foo;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class InitializeDb2 extends Builder {

    public InitializeDb2() {

    }

    @Override
    public final synchronized void execute() {
        Database database = Container.getDatabase(Helloworld2.helloworld2, "db2");
        try {
            database.createTable(Foo.class);
        } catch (ClassNotFoundException | SQLException | IllegalAccessException | IllegalArgumentException ex) {
            Logger.getLogger(InitializeDb2.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }
}
