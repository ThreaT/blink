package cool.blink.back.webserver;

import cool.blink.back.utilities.LogUtilities.Priority;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class Method {

    private Class clazz;
    private String name;
    private Class<?>[] parameterTypes;
    private java.lang.reflect.Method method;

    public Method(Class clazz, String name, Class<?>[] parameterTypes) {
        try {
            method = clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Method.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public java.lang.reflect.Method getMethod() {
        return method;
    }

    public void setMethod(java.lang.reflect.Method method) {
        this.method = method;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.clazz);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Arrays.deepHashCode(this.parameterTypes);
        hash = 23 * hash + Objects.hashCode(this.method);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Method other = (Method) obj;
        if (!Objects.equals(this.clazz, other.clazz)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.parameterTypes, other.parameterTypes)) {
            return false;
        }
        return Objects.equals(this.method, other.method);
    }

    @Override
    public String toString() {
        return "Method{" + "clazz=" + clazz + ", name=" + name + ", parameterTypes=" + Arrays.toString(parameterTypes) + ", method=" + method + '}';
    }

}
