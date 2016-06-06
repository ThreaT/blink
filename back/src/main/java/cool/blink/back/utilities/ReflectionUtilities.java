package cool.blink.back.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtilities {

    public static synchronized final Map<String, Object> objectToFieldsAndValuesMap(final Object object, final Boolean investigatePublicFields) throws IllegalArgumentException, IllegalAccessException {
        Class<? extends Object> c1 = object.getClass();
        Map<String, Object> map = new HashMap<>();
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (investigatePublicFields) {
                if (Modifier.isPublic(field.getModifiers())) {
                    Object value = field.get(object);
                    map.put(name, value);
                }
            } else {
                field.setAccessible(true);
                Object value = field.get(object);
                map.put(name, value);
            }
        }
        return map;
    }

    public static final synchronized List<Field> classToFieldsList(final Class clazz) throws IllegalArgumentException, IllegalAccessException {
        List<Field> privateFields = new ArrayList<>();
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field : allFields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                privateFields.add(field);
            }
        }
        return privateFields;
    }

    public static final synchronized Object getVariableValue(final Class<?> clazz, final String fieldName) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, SecurityException, InstantiationException {
        Object object = clazz.newInstance();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        if (field.isAccessible()) {
            try {
                object = field.get(null);
            } catch (NullPointerException ex) {
                object = field.get(object);
            }
        }
        return object;
    }

    public static final synchronized Boolean exists(final Class<?> clazz, final String fieldName) {
        try {
            clazz.getField(fieldName);
        } catch (NoSuchFieldException | SecurityException ex) {
            return false;
        }
        return true;
    }
}
