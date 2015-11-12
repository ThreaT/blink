package cool.blink.front.utilities;

import java.util.concurrent.atomic.AtomicLong;

public final class Longs {

    private static final AtomicLong uuid = new AtomicLong(100000000);

    public static final synchronized Long generateUniqueId() {
        return Longs.uuid.incrementAndGet();
    }

}
