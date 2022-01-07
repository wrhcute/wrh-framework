package io.github.wrhcute.utils;

import io.github.wrhcute.utils.time.SmartDate;
import org.junit.Test;

import java.text.ParseException;
import java.util.List;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName SmartDateTest.java
 * @Description TODO
 * @createTime 2022年01月06日 17:08:00
 */
public class SmartDateTest {

    @Test
    public void testListDay(){
        SmartDate smartDate = new SmartDate();
        List<SmartDate> smartDates = SmartDate.listDay(smartDate.firstDayOfMon(), smartDate.lastDayOfMon());
        for (SmartDate date : smartDates) {
            System.out.println(date);
        }
    }


    @Test
    public void testTryParse() throws ParseException {
        System.out.println(SmartDate.tryParse("2021-12-01 17:32:01.004"));
        System.out.println(SmartDate.tryParse("2021-12-01 17:32:01"));
        System.out.println(SmartDate.tryParse("2021-12-01"));
        System.out.println(SmartDate.tryParse("2021/12/01 14:32:01.005"));
        System.out.println(SmartDate.tryParse("2021.12.01 14:32:01.005"));
    }


    @Test
    public void testGetWeek(){
        System.out.println(new SmartDate().getWeek().toChinese());
    }

}
