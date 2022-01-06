package io.github.wrhcute.utils;

import java.util.StringJoiner;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName StringUtil.java
 * @Description TODO
 * @createTime 2022年01月06日 17:28:00
 */
public abstract class StringUtil {

    public static final String EMPTY = "";

    public static String repeat(String s,int count,String spilt){
        if (s == null)
            return null;
        if (count <= 0)
            return EMPTY;
        if (s.length() == 0 || count == 1)
            return s;
        char[] chars = new char[s.length() * count + spilt.length() * (count - 1)],stepCharArr = (s + spilt).toCharArray();
        int offset = 0 , lastPoint = chars.length - s.length() , stepping = stepCharArr.length;
        while (offset <= lastPoint){
            System.arraycopy(stepCharArr,0,chars,offset, offset == lastPoint ? stepping - spilt.length() : stepping);
            offset += stepping;
        }
        return new String(chars);
    }

    public static String repeat(String s , int count){
        return repeat(s,count,EMPTY);
    }

    public static String join(String delimiter,String prefix,String suffix, Object ... members){
        StringJoiner joiner = new StringJoiner(delimiter,prefix,suffix);
        for (Object member : members) {
            joiner.add(member.toString());
        }
        return joiner.toString();
    }

    public static String join(String delimiter, Object ... members){
        return join(delimiter,EMPTY,EMPTY,members);
    }

    public static String join(Object ... members){
        return join(EMPTY,members);
    }

}
