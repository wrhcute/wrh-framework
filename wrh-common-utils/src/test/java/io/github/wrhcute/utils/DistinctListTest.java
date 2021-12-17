package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName DistinctListTest.java
 * @Description
 * @createTime 2021年12月17日 15:24:00
 */
public class DistinctListTest {


    @Test
    public void test0(){
        DistinctList<String> list = new DistinctList<>();
        list.add("a");
        list.add("a");
        list.addAll(Colls.of("1","2","2","1"));
        System.out.println(list);
        assert list.size() == 3;
    }
}
