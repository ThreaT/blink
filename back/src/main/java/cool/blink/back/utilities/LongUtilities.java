package cool.blink.back.utilities;

import cool.blink.back.utilities.LogUtilities.Priority;
import java.util.Random;
import java.util.logging.Logger;

public class LongUtilities {

    public static final synchronized Long generateUniqueTimeInMillis() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(LongUtilities.class.getName()).log(Priority.HIGHEST, null, ex);
        }
        return System.currentTimeMillis();
    }

    public static final synchronized Long generateRandomNumber(final Integer from, final Integer to) {
        Random random = new Random();
        return (long) random.nextInt(to) + from;
    }

}
