package io.github.wrhcute.utils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName ReflectionUtil.java
 * @Description 反射工具类
 * @createTime 2021年12月16日 10:11:00
 */
@SuppressWarnings("unchecked")
public abstract class Reflections {

    public static Field getDeclaredField(Class<?> clazz,String name){
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            throw Exceptions.toRuntime(e);
        }
    }

    public static List<Field> getDeclaredFields(Class<?> clazz,Function<Field,Boolean> filter){
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (filter == null || filter.apply(field)){
                fields.add(field);
            }
        }
        return fields;
    }

    public static List<Field> getDeclaredFields(Class<?> clazz){
        return getDeclaredFields(clazz,null);
    }

    public static Field getField(Class<?> clazz , String name){
        List<Field> fields = getFields(clazz, f -> f.getName().equals(name));
        if (fields.isEmpty())
            return null;
        if (fields.size() == 1)
            return fields.get(0);
        return fields.stream().filter(f -> f.getDeclaringClass() == clazz).findAny().orElseGet(() -> fields.get(0));
    }

    public static List<Field> getFields(Class<?> clazz, Function<Field,Boolean> filter){
        List<Field> fields = new ArrayList<>(getDeclaredFields(clazz, filter));
        Class<?>[] interfaces = clazz.getInterfaces();
        Class<?> superclass = clazz.getSuperclass();
        for (Class<?> interfaceClz : interfaces) {
            fields.addAll(getFields(interfaceClz,filter));
        }
        if (Object.class != superclass){
            fields.addAll(getFields(superclass,filter));
        }
        return fields;
    }

    public static List<Field> getFields(Class<?> clazz){
        return getFields(clazz,null);
    }

    public static <T> T getDeclaredFieldValue(Class<?> clazz,String fieldName,Object callObj){
        final Field f = getDeclaredField(clazz, fieldName);
        return getFieldValue(f,callObj);
    }

    public static <T> T getDeclaredStaticFieldValue(Class<?> clazz ,String fieldName){
        return getDeclaredFieldValue(clazz,fieldName,null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field field,Object callObj){
        try {
            field.setAccessible(true);
            return (T) field.get(callObj);
        } catch (IllegalAccessException e) {
            throw Exceptions.toRuntime(e);
        }
    }

    public static void setFieldValue(Field field, Object obj, Object value){
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            throw Exceptions.toRuntime(e);
        }
    }

    public static <T> T newInstance(Class<T> clazz,Object ... args){
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        try {
            Constructor<T> constructor = getConstructor(clazz,parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
           throw Exceptions.toRuntime(e);
        }
    }

    public static <T> T[] newArray(Class<T> componentType, int len){
        return (T[]) Array.newInstance(componentType, len);
    }

    public static void setArray(Object array, int index,Object value){
        Array.set(array,index,value);
    }

    public static boolean isArray(Object obj){
        return obj.getClass().isArray();
    }

    public static int getArrayLength(Object array){
        return Array.getLength(array);
    }


    public static void arrayForeach(Object array , BiConsumer<Integer,Object> consumer){
        int length = getArrayLength(array);
        for (int i = 0; i < length; i++) {
             Object component = Array.get(array, i);
             consumer.accept(i,component);
        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes){
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw Exceptions.toRuntime(e);
        }
    }

    public static Method findGetter(Class<?> clazz, String fieldName){
        Field field = getField(clazz, fieldName);
        if (field == null)
            return null;
        String methodName = StrUtil.underline2Hump("get_" + fieldName);
        try {
            return getMethod(clazz,methodName,field.getType());
        } catch (Exception ignore) {
            return null;
        }
    }

    public static Method findSetter(Class<?> clazz, String fieldName){
        Field field = getField(clazz, fieldName);
        if (field == null)
            return null;
        String methodName = StrUtil.underline2Hump("set_" + fieldName);
        try {
            return getMethod(clazz,methodName,field.getType());
        } catch (Exception ignore) {
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz,String name,Class<?> ... parameterTypes){
        try {
            return clazz.getMethod(name,parameterTypes);
        } catch (NoSuchMethodException e) {
            throw Exceptions.toRuntime(e);
        }
    }

    public static Object callMethod(Method method, Object obj , Object ... args){
        try {
            return method.invoke(obj,args);
        } catch (IllegalAccessException|InvocationTargetException e) {
            throw Exceptions.toRuntime(e);
        }
    }

}