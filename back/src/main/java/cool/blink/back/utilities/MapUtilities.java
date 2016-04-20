package cool.blink.back.utilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MapUtilities {

    public static final synchronized <K, V extends Comparable<? super V>> Map<K, V> sortAscendingByValue(final Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> stream = map.entrySet().stream();
        stream.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    public static final synchronized <K, V extends Comparable<? super V>> Map<K, V> sortDescendingByValue(final Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> stream = map.entrySet().stream();
        stream.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

}
