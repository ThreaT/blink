package cool.blink.back.database;

import cool.blink.back.core.Environment;
import cool.blink.back.utilities.LogUtilities;
import cool.blink.back.utilities.ReflectionUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class Builder {

    private final String structure;

    public Builder(final Environment... environment) {
        this.structure = "";
    }

    public Builder(final String structure, final Environment... environments) {
        this.structure = structure;
    }

    public String getStructure() {
        return structure;
    }

    public synchronized void execute() {

    }

    /**
     * Looks inside clazz for object instances named after the class with an
     * integer appendix starting from 1.
     *
     * For example, If Foo contains foo1, bar1, foo2, bar2 then
     * listObjects(Foo.class) will return foo1 and foo2 instances.
     *
     * @param clazz clazz
     * @return List&lt;Object&gt;
     */
    public static final synchronized List<Object> listDefaultObjects(final Class<? extends Dataset> clazz) {
        Integer count = 1;
        while (ReflectionUtilities.exists(clazz, clazz.getSimpleName().toLowerCase() + count)) {
            count++;
        }
        List<Object> list = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            try {
                Object object = ReflectionUtilities.getVariableValue(clazz, clazz.getSimpleName().toLowerCase() + i);
                list.add(object);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                Logger.getLogger(ReflectionUtilities.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
            }
        }
        return list;
    }

}
