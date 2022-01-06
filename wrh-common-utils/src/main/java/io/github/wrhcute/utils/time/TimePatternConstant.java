package io.github.wrhcute.utils.time;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName TimeFormatConstant.java
 * @Description TODO
 * @createTime 2022年01月06日 15:46:00
 */
public enum TimePatternConstant {
    NORMAL("yyyy-MM-dd HH:mm:ss"),
    NORMAL_FULL("yyyy-MM-dd HH:mm:ss.SSS"),
    NORMAL_YMD("yyyy-MM-dd")
    ;

    public final String pattern;
    TimePatternConstant(String pattern){
        this.pattern = pattern;
    }

    public static String[] allPatterns(){
        TimePatternConstant[] values = values();
        String[] patterns = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            patterns[i] = values[i].pattern;
        }
        return patterns;
    }

}
