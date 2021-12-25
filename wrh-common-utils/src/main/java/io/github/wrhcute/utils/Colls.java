package io.github.wrhcute.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName Colls.java
 * @Description TODO
 * @createTime 2021年12月17日 15:26:00
 */
public abstract class Colls {


    @SafeVarargs
    public static <T> List<T> of(T ... element){
        return Arrays.asList(element);
    }

    @SafeVarargs
    public static <T> List<T> fit(Collection<T> ... collArr){
        if (collArr == null){
            return null;
        }else if (collArr.length == 0){
            return new ArrayList<>();
        }else{
            Collection<T> c = collArr[0];
            for (int i = 1; i < collArr.length; i++) {
                if (collArr[i] == null)
                    continue;
                c.addAll(collArr[i]);
            }
            return new ArrayList<>(c);
        }
    }
}
