package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName StringUtilTest.java
 * @Description TODO
 * @createTime 2022年01月06日 18:11:00
 */
public class StringUtilTest {


    @Test
    public void repeatTest(){
        System.out.println(StringUtil.repeat("s%",5));
        System.out.println(StringUtil.repeat("s%",1));
        System.out.println(StringUtil.repeat("s%",0));
        System.out.println(StringUtil.repeat("",5));
        System.out.println(StringUtil.repeat("AA",5,","));
    }
}
