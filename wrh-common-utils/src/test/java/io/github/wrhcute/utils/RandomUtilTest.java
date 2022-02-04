package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName RandomUtilTest.java
 * @Description TODO
 * @createTime 2022年02月04日 21:29:00
 */
public class RandomUtilTest {


    @Test
    public void randomIntTest(){
        for (int i = 0; i < 20; i++) {
            System.out.println(RandomUtil.random(0,1));
        }
        System.out.println("============================");
        for (int i = 0; i < 20; i++) {
            System.out.println(RandomUtil.random(0,4));
        }
    }

    @Test
    public void randomFloatTest(){
        for (int i = 0; i < 20; i++) {
            System.out.println(RandomUtil.random(0f,1f,2));
        }
        System.out.println("============================");
        for (int i = 0; i < 20; i++) {
            System.out.println(RandomUtil.random(0f,4f,2));
        }
    }
}
