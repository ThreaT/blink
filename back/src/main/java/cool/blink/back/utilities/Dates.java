package cool.blink.back.utilities;

import java.util.Date;
import org.joda.time.DateTime;

public class Dates {

    public static final Long dateDifferenceInMillis(final Date before, final Date after) {
        return after.getTime() - before.getTime();
    }

    public static final Long dateDifferenceInMillis(final DateTime before, final DateTime after) {
        return after.getMillis() - before.getMillis();
    }

}
