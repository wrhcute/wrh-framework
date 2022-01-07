package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName AssertsTest.java
 * @Description TODO
 * @createTime 2022年01月07日 16:16:00
 */
public class AssertsTest {

    @Test
    public void testMessage(){
        try {
            Asserts.notNullParam(null,"name","age","addr");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Asserts.isTrue(false,"{},ni{}","wrh","好");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
