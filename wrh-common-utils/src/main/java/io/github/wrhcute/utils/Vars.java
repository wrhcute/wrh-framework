package io.github.wrhcute.utils;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName Vars.java
 * @Description
 * @createTime 2021年12月17日 11:57:00
 */
public class Vars<T> {

    private T var;
    private final T def;

    public Vars(T var,T def){
        this.var = var;
        this.def = def;
    }

    public Vars(T var){
        this(var,null);
    }

    public T getVar() {
        return var == null ? def : var;
    }

    public T setVar(T var){
        T old = this.var;
        this.var = var;
        return old;
    }

    public static <T> T defaultIfNull(T object, T defaultValue) {
        return null != object ? object : defaultValue;
    }
}
