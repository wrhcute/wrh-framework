package io.github.wrhcute.utils.time;

import io.github.wrhcute.utils.Asserts;
import io.github.wrhcute.utils.Vars;

import java.text.DateFormat;
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
        calender.setTime(this);
        calender.add(Calendar.DATE,day);
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

    public String format(String format){
        return null;
    }

    private static SmartDate parse(CharSequence dateStr, DateFormat dateFormat) {
        Asserts.notNull(dateStr,"dataStr不能为空");
        try {
            return new SmartDate(dateFormat.parse(dateStr.toString()));
        } catch (Exception var4) {
            String pattern;
            if (dateFormat instanceof SimpleDateFormat) {
                pattern = ((SimpleDateFormat)dateFormat).toPattern();
            } else {
                pattern = dateFormat.toString();
            }

            throw new RuntimeException(String.format("Parse [%s] with format [%s] error!",dateStr, pattern));
        }
    }

    @Override
    public String toString() {
        return "";
    }
}
