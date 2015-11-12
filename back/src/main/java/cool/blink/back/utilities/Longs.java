package cool.blink.back.utilities;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Longs {

    private static final AtomicLong uuid = new AtomicLong(100000000);

    public static final synchronized Long generateUniqueId() {
        return Longs.uuid.incrementAndGet();
    }

    public static final synchronized Long generateUniqueTimeInMillis() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(Longs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return System.currentTimeMillis();
    }

    public static final synchronized Long generateRandomNumber(final Integer from, final Integer to) {
        Random random = new Random();
        return (long) random.nextInt(to) + from;
    }

}
