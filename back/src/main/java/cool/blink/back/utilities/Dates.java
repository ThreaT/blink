package cool.blink.back.utilities;

import java.util.Date;
import java.util.logging.Logger;
import org.joda.time.DateTime;

public class Dates {

    public static final Long dateDifferenceInMillis(final Date before, final Date after) {
        return after.getTime() - before.getTime();
    }

    public static final Long dateDifferenceInMillis(final DateTime before, final DateTime after) {
        return after.getMillis() - before.getMillis();
    }

    public static final synchronized DateTime generateUniqueDateTime() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(Longs.class.getName()).log(Logs.Priority.HIGHEST, null, ex);
        }
        return new DateTime();
    }

}
