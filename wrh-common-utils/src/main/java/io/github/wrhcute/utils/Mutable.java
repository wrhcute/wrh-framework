package io.github.wrhcute.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mutable {

    private Map<String,Field> fieldMap = new HashMap<>();

    private Mutable(){

    }

    public static Mutable load(Object obj){
        Class<?> clazz = obj.getClass();
        Mutable mutable = new Mutable();
        List<java.lang.reflect.Field> declaredFields = Reflections.getDeclaredFields(clazz);
        for (java.lang.reflect.Field field : declaredFields) {
            mutable.fieldMap.put(field.getName(),new Field(clazz,field.getName(), Reflections.getFieldValue(field,obj)));
        }
        return mutable;
    }

    public <T> T as(Class<T> target){
        return null;
    }

    private static class Field {
        private Class<?> source;
        private String name;
        private Object value;

        public Field(Class<?> source, String name, Object value) {
            this.source = source;
            this.name = name;
            this.value = value;
        }


    }

}
