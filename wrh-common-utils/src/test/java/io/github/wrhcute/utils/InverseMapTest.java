package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName InverseMapTest.java
 * @Description TODO
 * @createTime 2021年12月17日 18:04:00
 */
public class InverseMapTest {



    @Test
    public void test0(){
        InverseMap<String,Integer> inverseMap = new InverseMap<>();
        inverseMap.put("a",1);
        inverseMap.put("b",2);
        inverseMap.get("a");
        System.out.println(inverseMap);
        System.out.println("get by a => " + inverseMap.get("a"));
        System.out.println("inverseGet by 1 => " + inverseMap.inverseGet(1));

    }
}
