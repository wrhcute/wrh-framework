package io.github.wrhcute.utils.time;

import io.github.wrhcute.utils.Asserts;
import io.github.wrhcute.utils.ExceptionUtil;
import io.github.wrhcute.utils.Vars;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName SmartDate.java
 * @Description TODO
 * @createTime 2022年01月06日 11:41:00
 */
public class SmartDate extends Date {

    private final TimeZone timeZone;
    private final Week headWeek;

    public SmartDate() {
        this(System.currentTimeMillis());
    }

    public SmartDate(long date){
      this(date,null,null);
    }

    public SmartDate(Date date){
        this(date.getTime());
    }

    public SmartDate(long date,TimeZone timeZone,Week headWeek) {
        super(date);
        this.timeZone = Vars.defaultIfNull(timeZone,TimeZone.getDefault());
        this.headWeek = Vars.defaultIfNull(headWeek,Week.MONDAY);
    }

    public Calendar getCalendar(Locale locale) {
        Calendar cal = Calendar.getInstance(timeZone, locale);
        cal.setFirstDayOfWeek(this.headWeek.getValue());
        cal.setTime(this);
        return cal;
    }

    public Calendar getCalender(){
        return getCalendar(Locale.getDefault(Locale.Category.FORMAT));
    }

    public SmartDate addDay(int day){
        Calendar calender = getCalender();
        calender.add(Calendar.DATE,day);
        return new SmartDate(calender.getTime());
    }

    public SmartDate firstDayOfMon(){
        Calendar calender = getCalender();
        //获取某月最小天数
        int actualMinimum = calender.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        calender.set(Calendar.DAY_OF_MONTH, actualMinimum);
        return new SmartDate(calender.getTime());
    }

    public SmartDate lastDayOfMon(){
        Calendar calender = getCalender();
        int actualMaximum = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
        calender.set(Calendar.DAY_OF_MONTH, actualMaximum);
        return new SmartDate(calender.getTime());
    }

    public static List<SmartDate> listDay(Date start, Date end){
        List<SmartDate> list = new ArrayList<>();
        SmartDate tmp = new SmartDate(start), max = new SmartDate(end);
        while (!tmp.after(max)){
            list.add(tmp);
            tmp = tmp.addDay(1);
        }
        return list;
    }

    public String format(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(this);
    }

    private static SmartDate parse(CharSequence dateStr, DateFormat dateFormat) throws ParseException {
        return new SmartDate(dateFormat.parse(dateStr.toString()));
    }

    public static SmartDate tryParse(String dateStr, String ... patterns) throws ParseException {
        for (String pattern : patterns) {
            try {
                return parse(dateStr,new SimpleDateFormat(pattern));
            } catch (ParseException ignored) {
            }
        }
        throw new ParseException(String.format("转换失败,日期字符串：%s,表达式集合：%s",dateStr,Arrays.toString(patterns)),-1);
    }

    public static SmartDate tryParse(String dateStr) throws ParseException {
        return tryParse(dateStr,TimePatternConstant.allPatterns());
    }

    @Override
    public String toString() {
        return format(TimePatternConstant.NORMAL_FULL.pattern);
    }
}
