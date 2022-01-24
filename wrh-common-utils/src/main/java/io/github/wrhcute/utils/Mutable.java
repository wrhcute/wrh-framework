package io.github.wrhcute.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unchecked")
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
            this.propMap.put(name, new Prop(owner -> {
                Method getter = Reflections.findGetter(clazz, field.getName());
                if (getter != null) {
                    return Reflections.callMethod(getter, obj);
                } else {
                    return Reflections.getFieldValue(field, owner);
                }
            }, obj));
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
                } else if (!strict) {
                    Object targetValue = tryConvert(prop.originValue(), field.getType());
                    setTargetField(field, instance, targetValue);
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

    private static Object tryConvert(Object obj, Class<?> target) {
        Class<?> clz = obj.getClass();
        if (clz.isAssignableFrom(target)) {
            return obj;
        } else if (target == String.class) {
            Method toString = Reflections.getMethod(clz, "toString");
            if (toString.getDeclaringClass() != Object.class)//有重写toString
                return obj.toString();
        } else if (target.isAssignableFrom(Number.class)) {
            Class<? extends Number> numClzTarget = (Class<? extends Number>) target;
            if (clz.isAssignableFrom(Number.class)) {
                Number number = ((Number) obj);
                return numberConvert(number, numClzTarget);
            } else if (clz.isArray()) {
                int length = ((Object[]) obj).length;
                return numberConvert(length, numClzTarget);
            } else if (clz.isAssignableFrom(Collection.class)) {
                int size = ((Collection<?>) obj).size();
                return numberConvert(size, numClzTarget);
            }
        } else if (target.isAssignableFrom(Collection.class)) {
            Class<? extends Collection<Object>> collClzTarget = (Class<? extends Collection<Object>>) target;
            if (obj instanceof Collection) {
                return collConvert((Collection<?>) obj, collClzTarget);
            } else if (obj instanceof Map) {
                Collection<?> collection = ((Map<?, ?>) obj).values();
                return collConvert(collection, collClzTarget);
            } else if (clz.isArray()) {
                Object[] arr = (Object[]) obj;
                Collection<?> collection = Arrays.asList(arr);
                return collConvert(collection, collClzTarget);
            } else if (clz == String.class) {
                String[] splits = ",;、 ".split("");
                int k = 0, max = 0;
                for (int i = 0; i < splits.length; i++) {
                    int total = StrUtil.totalSub(obj.toString(), splits[i]);
                    if (max < total) {
                        max = total;
                        k = i;
                    }
                }
                if (max != 0) {
                    return collConvert(Arrays.asList(obj.toString().split(splits[k])), collClzTarget);
                }
            } else if (target.isArray()) {
                Class<?> componentType = target.getComponentType();
                if (Reflections.isArray(obj)){
                    Class<?> clzComponentType = clz.getComponentType();
                    int len = Reflections.getArrayLength(obj);
                    if (clzComponentType.isAssignableFrom(componentType)){
                        return obj;
                    }else{

                    }

                }else if (obj instanceof Iterable){
                    Iterable<?> iterable = (Iterable<?>) obj;
                    List<Object> list = new ArrayList<>();
                    for (Object o : iterable) {
                        Object convert = tryConvert(o, componentType);
                        if (convert != null)
                            list.add(convert);
                    }
                    if (!list.isEmpty())
                        return list.toArray();
                }else{
                    Object o = tryConvert(obj, componentType);
                    if (o != null){
                        Object[] array = Reflections.newArray(componentType, 1);
                        array[0] = o;
                        return array;
                    }
                }

            }
        }
        return null;
    }


    private static <T extends Collection<Object>> T collConvert(Collection<?> coll, Class<T> target) {
        T instance = Reflections.newInstance(target);
        instance.addAll(coll);
        return instance;
    }

    private static <T extends Number> T numberConvert(Number number, Class<T> target) {
        if (target == Long.class || target == long.class)
            return (T) Long.valueOf(number.longValue());
        else if (target == Integer.class || target == int.class)
            return (T) Integer.valueOf(number.intValue());
        else if (target == Double.class || target == double.class)
            return (T) Double.valueOf(number.doubleValue());
        else if (target == Float.class || target == float.class)
            return (T) Float.valueOf(number.floatValue());
        else if (target == Short.class || target == short.class)
            return (T) Short.valueOf(number.shortValue());
        else if (target == BigInteger.class)
            return (T) BigInteger.valueOf(number.longValue());
        else if (target == BigDecimal.class)
            return (T) BigDecimal.valueOf(number.doubleValue());
        else
            return null;
    }

    private static class Prop {
        private final Function<Object, Object> originProvider;

        private final Object owner;

        public Prop(Function<Object, Object> originProvider, Object owner) {
            this.originProvider = originProvider;
            this.owner = owner;
        }

        Object originValue() {
            return originProvider.apply(owner);
        }

    }

}
