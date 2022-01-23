package io.github.wrhcute.utils;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName MutableTest.java
 * @Description TODO
 * @createTime 2022年01月22日 18:34:00
 */
public class MutableTest {


    @Test
    public void test(){
        final ParameterizedType genericSuperclass = (ParameterizedType) HashMap.class.getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        for (Type actualTypeArgument : actualTypeArguments) {
            System.out.println(actualTypeArgument.getTypeName());
        }
    }
}
