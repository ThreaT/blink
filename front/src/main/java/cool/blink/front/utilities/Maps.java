package cool.blink.front.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Maps {

    public static final synchronized Integer countParameters(Map.Entry<String, String>... parameter) {
        return Arrays.asList(parameter).size();
    }

    public static final synchronized Boolean isFirstEntry(List<Map.Entry<String, String>> parameters, Map.Entry<String, String> parameter) {
        for (Map.Entry<String, String> entry : parameters) {
            return entry.equals(parameter);
        }
        return false;
    }
}
