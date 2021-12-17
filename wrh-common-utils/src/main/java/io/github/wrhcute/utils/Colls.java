package io.github.wrhcute.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName Colls.java
 * @Description TODO
 * @createTime 2021年12月17日 15:26:00
 */
public abstract class Colls {


    public static <T> List<T> of(T ... element){
        return Arrays.asList(element);
    }
}
