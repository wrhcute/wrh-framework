package io.github.wrhcute.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Mutable {

    private Map<String, Prop> propMap = new HashMap<>();

    private Mutable() {

    }

    protected static String getPropName(Field field) {
        return field.getName();
    }

    public static Mutable load(Object... objects) {
        return new Mutable().loadObjs(objects);
    }

    public Mutable loadObjs(Object... objects) {
        for (Object obj : objects) {

        }
        return this;
    }


    private void loadObj(Object obj) {
        if (obj instanceof Map) {
            loadMap((Map<?, ?>) obj);
            return;
        }

        if (obj instanceof Mutable) {
            loadSelf((Mutable) obj);
            return;
        }

        Class<?> clazz = obj.getClass();
        List<Field> fields = Reflections.getFields(clazz);
        for (Field field : fields) {
            String name = getPropName(field);
            this.propMap.put(name, new Prop(owner -> Reflections.getFieldValue(field, owner), obj));
        }
    }

    private void loadMap(Map<?, ?> map) {
        for (Object key : map.keySet()) {
            this.propMap.put(key.toString(), new Prop(owner -> ((Map<?, ?>) owner).get(key), map));
        }
    }

    private void loadSelf(Mutable mutable) {
        this.propMap.putAll(mutable.propMap);
    }

    public <T> T as(Class<T> target, boolean strict, Object... initArgs) {
        if (target.isAssignableFrom(Mutable.class)) {
            Mutable same = new Mutable();
            same.propMap = this.propMap;
            return (T) same;
        }
        if (target.isAssignableFrom(Map.class)) {
            Class<? extends Map<String, Object>> mapClz = (Class<? extends Map<String, Object>>) target;
            Map<String, Object> map = Reflections.newInstance(mapClz);
            for (String s : this.propMap.keySet()) {
                map.put(s, this.propMap.get(s).originValue());
            }
            return (T) map;
        }
        List<Field> fields = Reflections.getFields(target);
        T instance = Reflections.newInstance(target, initArgs);
        for (Field field : fields) {
            String name = getPropName(field);
            if (propMap.containsKey(name)) {
                Prop prop = propMap.get(name);
                Class<?> targetType = field.getType(), sourceType = prop.originValue().getClass();
                if (targetType == sourceType || sourceType.isAssignableFrom(targetType)) {
                    Object targetValue = prop.originValue();
                    setTargetField(field, instance, targetValue);
                } else if (strict) {
                    continue;
                } else {

                }
            }
        }
        return instance;
    }


    private static void setTargetField(Field targetField, Object instance, Object fieldValue) {
        Method setter = Reflections.findSetter(targetField.getDeclaringClass(), targetField.getName());
        if (setter == null) {
            Reflections.setFieldValue(targetField, instance, fieldValue);
        } else {
            Reflections.callMethod(setter, instance, fieldValue);
        }
    }

    private static class Prop {
        private final Function<Object, Object> sourceFunc;
        //属性拥有者
        private final Object owner;

        public Prop(Function<Object, Object> sourceFunc, Object owner) {
            this.sourceFunc = sourceFunc;
            this.owner = owner;
        }

        Object originValue() {
            return sourceFunc.apply(owner);
        }

    }

}
