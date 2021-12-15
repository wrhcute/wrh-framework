package io.github.wrhcute.utils;

import org.junit.Test;

import java.time.LocalTime;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName SlidingTimeWindowTest.java
 * @Description TODO
 * @createTime 2021年12月15日 18:51:00
 */

public class SlidingTimeWindowTest {

    @Test
    public void test() {
        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(1000,10);
        Thread checkThread = new Thread(() -> {
            while (true){
                int count = slidingTimeWindow.currentWindowCount();
                System.out.println(LocalTime.now().toString() + ">>>window count:" + count);
                sleep(10);
            }
        });
        checkThread.start();
        while (true){
            slidingTimeWindow.incrBy(1);
            sleep(1);
        }

    }

    private static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
