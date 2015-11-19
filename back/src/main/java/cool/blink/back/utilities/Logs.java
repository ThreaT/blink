package cool.blink.back.utilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logs {

    public static final synchronized void setAllLoggersToWriteToFile(final String filename, final Integer maxBytesFileSize, final Level level) {
        try {
            Handler handler = new FileHandler(filename, maxBytesFileSize, 1);
            handler.setLevel(level);
            Logger.getLogger("").addHandler(handler);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Logs.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

    public static class Priority extends Level {

        public static final Level LOWEST = new Priority("LOWEST", 1100);
        public static final Level LOW = new Priority("LOW", 1200);
        public static final Level MEDIUM = new Priority("MEDIUM", 1300);
        public static final Level HIGH = new Priority("HIGH", 1400);
        public static final Level HIGHEST = new Priority("HIGHEST", 1500);

        public Priority(String name, int value) {
            super(name, value);
        }
    }

}
