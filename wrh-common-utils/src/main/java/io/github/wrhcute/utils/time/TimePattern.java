package io.github.wrhcute.utils.time;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName TimeFormatConstant.java
 * @Description TODO
 * @createTime 2022年01月06日 15:46:00
 */
public enum TimePattern {
    NORMAL_FULL("yyyy-MM-dd HH:mm:ss.SSS"),
    NORMAL("yyyy-MM-dd HH:mm:ss"),
    NORMAL_YMD("yyyy-MM-dd"),
    NUMBER_ONLY_FULL("yyyyMMddHHmmssSSS"),
    NUMBER_ONLY("yyyyMMddHHmmss"),
    NUMBER_ONLY_YMD("yyyyMMdd"),
    INCLINE_FULL("yyyy/MM/dd HH:mm:ss.SSS"),
    INCLINE("yyyy/MM/dd HH:mm:ss"),
    INCLINE_YMD("yyyy/MM/dd"),
    DOT_FULL("yyyy.MM.dd HH:mm:ss.SSS"),
    DOT("yyyy.MM.dd HH:mm:ss"),
    DOT_YMD("yyyy.MM.dd"),
    ISO8601("yyyy-MM-dd HH:mm:ss,SSS"),
    CHINESE("yyyy年MM月dd日HH时mm分ss秒"),
    CHINESE_YMD("yyyy年MM月dd日")
    ;
    TimePattern(String pattern){
        this.pattern = pattern;
    }

    public final String pattern;
    public static final String[] allPatterns  = new String[values().length];


    static {
        for (int i = 0; i < values().length; i++) {
            allPatterns[i] = values()[i].pattern;
        }
    }

}
