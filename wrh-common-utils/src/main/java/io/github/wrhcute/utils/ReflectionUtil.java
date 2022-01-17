package io.github.wrhcute.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName ReflectionUtil.java
 * @Description 反射工具类
 * @createTime 2021年12月16日 10:11:00
 */
public abstract class ReflectionUtil {

    public static Field getDeclaredField(Class<?> clazz,String name){
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            throw ExceptionUtil.toRuntime(e);
        }
    }

    public static List<Field> getDeclaredFields(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.asList(fields);
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
            throw ExceptionUtil.toRuntime(e);
        }
    }
}
