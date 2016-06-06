package cool.blink.examples.helloworld1;

import cool.blink.back.core.Container;
import cool.blink.back.database.Builder;
import cool.blink.back.database.Database;
import cool.blink.back.utilities.LogUtilities.Priority;
import cool.blink.examples.helloworld1.table.Foo;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class InitializeDb5 extends Builder {

    public InitializeDb5() {

    }

    @Override
    public final synchronized void execute() {
        Database database = Container.getDatabase(Helloworld1.helloworld1, "db5");
        try {
            database.createTable(Foo.class);
        } catch (ClassNotFoundException | SQLException | IllegalArgumentException ex) {
            Logger.getLogger(InitializeDb5.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }
}
