package cool.blink.back.utilities;

import java.util.List;

public class Lists {

    public static final synchronized Boolean containsIgnoreCase(final String string, final List<String> list) {
        return list.stream().anyMatch((s) -> (string.equalsIgnoreCase(s)));
    }

    public static final synchronized Boolean containEqualContents(final List<String> list1, final List<String> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (String string : list1) {
            if (!list2.contains(string)) {
                return false;
            }
        }

        for (String string : list2) {
            if (!list1.contains(string)) {
                return false;
            }
        }
        return true;
    }

}
