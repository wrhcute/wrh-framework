package io.github.wrhcute.utils;

import java.lang.reflect.Field;

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

    public static <T> T getDeclaredFieldValue(Class<?> clazz,String fieldName,Object param){
        final Field f = getDeclaredField(clazz, fieldName);
        return getFieldValue(f,param);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field field,Object param){
        try {
            field.setAccessible(true);
            return (T) field.get(param);
        } catch (IllegalAccessException e) {
            throw ExceptionUtil.toRuntime(e);
        }
    }
}
