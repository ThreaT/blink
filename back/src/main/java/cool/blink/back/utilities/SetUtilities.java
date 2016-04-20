package cool.blink.back.utilities;

import java.util.Iterator;
import java.util.Set;

public class SetUtilities {

    public static final synchronized Object get(Set set, Object object) {
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Object current = iterator.next();
            if (current.equals(object)) {
                return current;
            }
        }
        return null;
    }

}
