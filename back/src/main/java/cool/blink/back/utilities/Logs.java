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
            Logger.getLogger(Logs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
