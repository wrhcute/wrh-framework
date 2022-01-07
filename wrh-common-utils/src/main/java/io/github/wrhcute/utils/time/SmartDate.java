package io.github.wrhcute.utils.time;

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

    public SmartDate() {
        this(System.currentTimeMillis());
    }

    public SmartDate(long date){
      this(date,null);
    }

    public SmartDate(Date date){
        this(date.getTime());
    }

    public SmartDate(long date,TimeZone timeZone) {
        super(date);
        this.timeZone = Vars.defaultIfNull(timeZone,TimeZone.getDefault());
    }

    public Calendar getCalendar(Locale locale) {
        Calendar cal = Calendar.getInstance(timeZone, locale);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
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

    public SmartDate firstDayOfWeek(){
        Calendar calender = getCalender();
        calender.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SmartDate(calender.getTime());
    }

    public SmartDate lastDayOfWeek(){
        Calendar calender = getCalender();
        calender.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
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

    public Week getWeek(){
        Calendar calender = getCalender();
        return Week.of(calender.get(Calendar.DAY_OF_WEEK));
    }

    public Integer year(){
        return getCalender().get(Calendar.YEAR);
    }

    private static SmartDate parse(CharSequence dateStr, DateFormat dateFormat) throws ParseException {
        return new SmartDate(dateFormat.parse(dateStr.toString()));
    }

    public static SmartDate parse(String dateStr, String ... patterns) throws ParseException {
        for (String pattern : patterns) {
            try {
                return parse(dateStr,new SimpleDateFormat(pattern));
            } catch (ParseException ignored) {
            }
        }
        throw new ParseException(String.format("转换失败,日期字符串：%s,表达式集合：%s",dateStr,Arrays.toString(patterns)),-1);
    }

    public static SmartDate tryParse(String dateStr, String ... patterns) {
        try {
            return parse(dateStr,patterns);
        } catch (ParseException e) {
            return null;
        }
    }
    public static SmartDate tryParse(String dateStr){
        return tryParse(dateStr,TimePatternConstant.allPatterns());
    }

    @Override
    public String toString() {
        return format(TimePatternConstant.NORMAL_FULL.pattern);
    }
}
