package io.github.wrhcute.utils.time;

import io.github.wrhcute.utils.StrUtil;

import java.util.Calendar;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName Week.java
 * @Description TODO
 * @createTime 2022年01月06日 16:57:00
 */
public enum Week {
    SUNDAY(Calendar.SUNDAY,"日"),
    MONDAY(Calendar.MONDAY,"一"),
    TUESDAY(Calendar.TUESDAY,"二"),
    WEDNESDAY(Calendar.WEDNESDAY,"三"),
    THURSDAY(Calendar.THURSDAY,"四"),
    FRIDAY(Calendar.FRIDAY,"五"),
    SATURDAY(Calendar.SATURDAY,"六");

    private final int calendarValue;
    private final String chineseSuffix;

    Week(int calendarValue, String chineseSuffix) {
        this.calendarValue = calendarValue;
        this.chineseSuffix = chineseSuffix;
    }

    public int getCalendarValue() {
        return this.calendarValue;
    }

    public String toChinese() {
        return this.toChinese("星期");
    }

    public String toChinese(String weekNamePre) {
        return StrUtil.joins(weekNamePre,this.chineseSuffix);
    }

    public static Week of(int calendarWeekValue) {
        for (Week week : values()) {
            if (week.calendarValue == calendarWeekValue)
                return week;
        }
        return null;
    }

    public Week toggle(int step){
        int stepTo;
        int to = (stepTo = (this.calendarValue + step) % 7) == 0 ? 7 : stepTo;
        for (Week week : values()) {
            if (week.calendarValue == to)
                return week;
        }
        throw new RuntimeException(Week.class.getCanonicalName() + "成员异常");
    }

    public int gap(Week week){
        return (week.calendarValue + 7 - this.calendarValue) % 7 - 1;
    }
}
