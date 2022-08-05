package io.github.wrhcute.utils;

import io.github.wrhcute.utils.time.SDate;
import io.github.wrhcute.utils.time.Week;
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
public class SDateTest {



    @Test
    public void test(){
        System.out.println("getYearNum =" + new SDate().getYearNum());
        System.out.println("getMonNum =" + new SDate().getMonNum());
        System.out.println("getDayNum =" + new SDate().getDayNum());
        System.out.println("getYearMonNum =" + new SDate().getYearMonNum());
        System.out.println("getHourNum =" + new SDate().getHourNum());
        System.out.println("getMinuteNum =" + new SDate().getMinuteNum());
        System.out.println("getSecNum =" + new SDate().getSecNum());
        System.out.println("getHmsNum =" + new SDate().getHmsNum());
        System.out.println("getYmdNum =" + new SDate().getYmdNum());
        System.out.println("getTimeNum =" + new SDate().getTimeNum());
        System.out.println("getWeekNum =" + new SDate().getWeekNum());
    }

    @Test
    public void testListDay(){
        SDate smartDate = new SDate();
        List<SDate> smartDates = SDate.listDay(smartDate.firstDayOfMon(), smartDate.lastDayOfMon());
        for (SDate date : smartDates) {
            System.out.println(date);
        }
    }


    @Test
    public void testTryParse() throws ParseException {
        System.out.println(SDate.tryParse("2021-12-01 17:32:01.004"));
        System.out.println(SDate.tryParse("2021-12-01 17:32:01"));
        System.out.println(SDate.tryParse("2021-12-01"));
        System.out.println(SDate.tryParse("2021/12/01 14:32:01.005"));
        System.out.println(SDate.tryParse("2021.12.01 14:32:01.005"));
    }


    @Test
    public void testGetWeek(){
        System.out.println(new SDate().getWeek().toChinese());
        System.out.println(new SDate().getWeek().gap(Week.MONDAY));
        System.out.println(new SDate().getWeek().toggle(3));
        System.out.println(Week.MONDAY.gap(Week.SUNDAY));
    }

    @Test
    public void testBoundDay(){
        System.out.println("当前周的第一天:" + new SDate().firstDayOfWeek());
        System.out.println("当前周的最后一天:" + new SDate().lastDayOfWeek());
        System.out.println("当前天的第一秒:" + new SDate().firstTimeOfDay());
        System.out.println("当前天的最后一秒:" + new SDate().lastTimeOfDay());
    }

}
