package cool.blink.examples.interfaces;

import cool.blink.front.Autorunner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Autosaver {

    /*
     * Before running this, if you're using Netbeans follow these steps:
     * 1. Tools -> Options -> Java -> Java Debugger -> General -> Apply code changes after save (in "Compile on Save" mode only) -> Apply
     * 2. Right click interfaces project -> Properties -> Compile -> Compile on Save -> Ok
     * 3. Press Ctrl+Shift+F5 within Autosaver.java
     * 4. After making changes to ResponsiveDivs.java you should be able to refresh ResponsiveDivs.html and view the changes instantly
     */
    public static void main(String args[]) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Autorunner(ResponsiveDivs.class, "ResponsiveDivs.html"), 0, 2, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Autorunner(ResponsiveHeader.class, "ResponsiveHeader.html"), 0, 2, TimeUnit.SECONDS);
    }

}
