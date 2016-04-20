package cool.blink.back.utilities;

import java.util.List;

public class ListUtilities {

    public static final synchronized Boolean containsIgnoreCase(final String string, final List<?> list) {
        for (Object object : list) {
            if (string.toLowerCase().contains(object.toString().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static final synchronized Integer totalEqualsIgnoreCase(final String string, final List<String> list) {
        Integer total = 0;
        for (String temp : list) {
            if (temp.equalsIgnoreCase(string)) {
                total++;
            }
        }
        return total;
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
