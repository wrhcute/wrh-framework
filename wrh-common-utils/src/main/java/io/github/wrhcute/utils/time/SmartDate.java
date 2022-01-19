package io.github.wrhcute.utils.time;

import io.github.wrhcute.utils.StrUtil;
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

    private final long SECOND_UNIT = 1000;
    private final long MINUTE_UNIT = SECOND_UNIT * 60;
    private final long HOUR_UNIT = MINUTE_UNIT * 60;
    private final long DAY_UNIT = HOUR_UNIT * 24;

    public SmartDate() {
        this(System.currentTimeMillis());
    }

    public SmartDate(long date){
      this(date,null,Week.MONDAY);
    }

    public SmartDate(Date date){
        this(date.getTime());
    }

    public SmartDate(long date,TimeZone timeZone,Week headWeek) {
        super(date);
        this.timeZone = Vars.defaultIfNull(timeZone,TimeZone.getDefault());
        this.headWeek = headWeek;
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

    public SmartDate offsetDay(int day){
        Calendar calender = getCalender();
        calender.add(Calendar.DATE,day);
        return new SmartDate(calender.getTime());
    }

    public SmartDate offsetHour(int hour){
        Calendar calender = getCalender();
        calender.add(Calendar.HOUR,hour);
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
        calender.set(Calendar.DAY_OF_WEEK, headWeek.getCalendarValue());
        return new SmartDate(calender.getTime());
    }

    public SmartDate lastDayOfWeek(){
        Calendar calender = getCalender();
        calender.set(Calendar.DAY_OF_WEEK, headWeek.toggle(6).getCalendarValue());
        return new SmartDate(calender.getTime());
    }

    public SmartDate firstTimeOfDay(){
        Calendar calender = getCalender();
        calender.set(Calendar.HOUR_OF_DAY,0);
        calender.set(Calendar.MINUTE,0);
        calender.set(Calendar.SECOND,0);
        calender.set(Calendar.MILLISECOND,0);
        return new SmartDate(calender.getTime());
    }

    public SmartDate lastTimeOfDay(){
        Calendar calender = getCalender();
        calender.set(Calendar.HOUR_OF_DAY,23);
        calender.set(Calendar.MINUTE,59);
        calender.set(Calendar.SECOND,59);
        calender.set(Calendar.MILLISECOND,999);
        return new SmartDate(calender.getTime());
    }

    public static List<SmartDate> listDay(Date start, Date end){
        List<SmartDate> list = new ArrayList<>();
        SmartDate tmp = new SmartDate(start), max = new SmartDate(end);
        while (!tmp.after(max)){
            list.add(tmp);
            tmp = tmp.offsetDay(1);
        }
        return list;
    }

    public String format(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(this);
    }

    public String format(TimePattern pattern){
        return format(pattern.pattern);
    }

    public Week getWeek(){
        Calendar calender = getCalender();
        return Week.of(calender.get(Calendar.DAY_OF_WEEK));
    }

    public Integer getWeekNum(){
        return getWeek().gap(headWeek) + 1;
    }

    public Integer getDayNum(){
       return getCalender().get(Calendar.DAY_OF_MONTH);
    }

    public Integer getYearNum(){
        return getCalender().get(Calendar.YEAR);
    }

    public Integer getMonNum(){
        return getCalender().get(Calendar.MONTH) + 1;
    }

    public Integer getYearMonNum(){
        return Integer.valueOf(StrUtil.joins(getYearNum(),StrUtil.paddingLeft(getMonNum().toString(),"0",2)));
    }

    public Integer getYmdNum(){
        return Integer.valueOf(StrUtil.joins(getYearMonNum(),StrUtil.paddingLeft(getDayNum().toString(),"0",2)));
    }

    public Integer getHourNum(){
        return getCalender().get(Calendar.HOUR_OF_DAY);
    }

    public Integer getMinuteNum(){
        return getCalender().get(Calendar.MINUTE);
    }

    public Integer getSecNum(){
        return getCalender().get(Calendar.SECOND);
    }

    public Integer getHmNum(){
        String paddingHour = StrUtil.paddingLeft(getHourNum().toString(),"0",2);
        String paddingMinute = StrUtil.paddingLeft(getMinuteNum().toString(),"0",2);
        return Integer.valueOf(StrUtil.joins(paddingHour,paddingMinute));
    }

    public Integer getHmsNum(){
        String paddingSec =  StrUtil.paddingLeft(getSecNum().toString(),"0",2);
        return Integer.valueOf(StrUtil.joins(getHmNum(),paddingSec));
    }

    public Long getTimeNum(){
        return Long.valueOf(StrUtil.joins(getYmdNum(),StrUtil.paddingLeft(getHmsNum().toString(),"0",6)));
    }

    private static SmartDate parse(CharSequence dateStr, DateFormat dateFormat) throws ParseException {
        return new SmartDate(dateFormat.parse(dateStr.toString()));
    }

    public static SmartDate parse(String dateStr, TimePattern ... pattern)throws ParseException{
        return parse(dateStr, Arrays.stream(pattern).map(p -> p.pattern).toArray(String[]::new));
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

    public static SmartDate tryParse(String dateStr, TimePattern ... pattern){
        try {
            return parse(dateStr, Arrays.stream(pattern).map(p -> p.pattern).toArray(String[]::new));
        } catch (ParseException e) {
            return null;
        }
    }

    public static SmartDate tryParse(String dateStr, String ... patterns) {
        try {
            return parse(dateStr,patterns);
        } catch (ParseException e) {
            return null;
        }
    }
    public static SmartDate tryParse(String dateStr){
        return tryParse(dateStr, TimePattern.allPatterns);
    }

    public int hourBetween(Date after ,boolean abs){
        long start ,end;
        if (abs && after.before(this)){
            start = after.getTime();
            end = this.getTime();
        }else{
            start = this.getTime();
            end = after.getTime();
        }
        return (int) ((end - start) / HOUR_UNIT);
    }

    public int hourBetween(Date after){
        return hourBetween(after,false);
    }

    public static int hourBetween(Date before ,Date after , boolean abs){
        return new SmartDate(before).hourBetween(after,abs);
    }

    public static int hourBetween(Date before ,Date after){
        return hourBetween(before,after,false);
    }

    @Override
    public String toString() {
        return format(TimePattern.NORMAL_FULL);
    }
}
