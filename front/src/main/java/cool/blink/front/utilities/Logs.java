package cool.blink.front.utilities;

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
            Logger.getLogger(Logs.class.getName()).log(CustomLevel.HIGHEST, null, ex);
        }
    }

    public static class CustomLevel extends Level {

        public static final Level LOWEST = new CustomLevel("LOWEST", 1100);
        public static final Level LOW = new CustomLevel("LOW", 1200);
        public static final Level MEDIUM = new CustomLevel("MEDIUM", 1300);
        public static final Level HIGH = new CustomLevel("HIGH", 1400);
        public static final Level HIGHEST = new CustomLevel("HIGHEST", 1500);

        public CustomLevel(String name, int value) {
            super(name, value);
        }
    }

}
