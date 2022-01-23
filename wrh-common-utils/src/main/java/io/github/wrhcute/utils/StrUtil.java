package io.github.wrhcute.utils;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName StrUtil.java
 * @Description TODO
 * @createTime 2022年01月06日 17:28:00
 */
public abstract class StrUtil {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String TAB = "  ";
    public static final String PLACEHOLDER = "\\{}";
    private static final Pattern UNDERLINE_PATTERN = Pattern.compile("_(\\w)");

    public static String repeat(String s, int count, String delimiter) {
        if (s == null)
            return null;
        if (count <= 0)
            return EMPTY;
        if (s.length() == 0 || count == 1)
            return s;
        char[] chars = new char[s.length() * count + delimiter.length() * (count - 1)], stepCharArr = (s + delimiter).toCharArray();
        int offset = 0, lastPoint = chars.length - s.length(), stepping = stepCharArr.length;
        while (offset <= lastPoint) {
            System.arraycopy(stepCharArr, 0, chars, offset, offset == lastPoint ? stepping - delimiter.length() : stepping);
            offset += stepping;
        }
        return new String(chars);
    }

    public static String repeat(String s, int count) {
        return repeat(s, count, EMPTY);
    }

    public static String join(String delimiter, String prefix, String suffix, Object... members) {
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        for (Object member : members) {
            joiner.add(member.toString());
        }
        return joiner.toString();
    }

    public static String join(String delimiter, Object... members) {
        return join(delimiter, EMPTY, EMPTY, members);
    }

    public static String joins(Object... members) {
        return join(EMPTY, members);
    }

    public static String format(String template, Object... params) {
        if (template == null || template.length() == 0 || params.length == 0)
            return template;
        String[] strings = (template + SPACE).split(PLACEHOLDER);
        int len = strings.length + Math.min(params.length, strings.length - 1);
        Object[] formatArr = new Object[len];
        for (int i = 0,j = 0; i < len; i += 2,j++) {
            formatArr[i] = strings[j];
            if ( i + 1 < len){
                if (j < params.length){
                    formatArr[i + 1] = params[j];
                }else if (j + 1 < strings.length){
                    formatArr[i + 1] = strings[++j];
                }else{
                    formatArr[i + 1] = EMPTY;
                }
            }
        }
        String joins = joins(formatArr);
        return joins.substring(0,joins.length() - 1);
    }


    public static String padding(String s, String padding, int len, boolean right) {
        if (len == 0)
            return EMPTY;
        if (EMPTY.equals(padding) || len <= s.length())
            return s;
        int add = (len - s.length()) / padding.length() + 1;
        String temp = right ? joins(s,repeat(padding,add)) : joins(repeat(padding,add),s);
        return right ? temp.substring(0,len) : temp.substring(temp.length() - len);
    }

    public static String paddingLeft(String s, String padding, int len){
        return padding(s,padding,len,false);
    }


    public static String paddingRight(String s, String padding, int len){
        return padding(s,padding,len,true);
    }

    public static String underline2Hump(String str){
        str = str.toLowerCase();
        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
