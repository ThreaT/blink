package cool.blink.front.utilities;

import java.util.Arrays;
import java.util.List;

public class Strings {

    public static final synchronized String generateSpacedStringValues(String... strings_) {
        List<String> allStrings = Arrays.asList(strings_);
        String strings = "";
        for (String string : allStrings) {
            strings += " " + string;
        }
        return strings;
    }
}
