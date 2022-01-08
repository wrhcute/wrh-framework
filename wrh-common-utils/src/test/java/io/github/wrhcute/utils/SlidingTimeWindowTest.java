package io.github.wrhcute.utils;

import io.github.wrhcute.utils.time.SlidingTimeWindow;
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

    volatile boolean check = true;
    @Test
    public void test() {
        SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(1000,10);

        Thread checkThread = new Thread(() -> {
            while (check){
                int count = slidingTimeWindow.currentWindowCount();
                System.out.println(LocalTime.now().toString() + ">>>window count:" + count);
                sleep(10);
            }
        });
        checkThread.start();
        for (int i = 0; i < 2000; i++) {
            slidingTimeWindow.incrBy(1);
            sleep(1);
        }
        check = false;
    }

    private static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
