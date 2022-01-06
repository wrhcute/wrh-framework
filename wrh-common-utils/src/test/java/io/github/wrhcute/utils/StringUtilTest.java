package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName StringUtilTest.java
 * @Description TODO
 * @createTime 2022年01月06日 18:11:00
 */
public class StringUtilTest {


    @Test
    public void repeatTest(){
        String s1 = StringUtil.repeat("s%",5);
        System.out.println("repeat(\"s%\",5) =" + s1);
        assert "s%s%s%s%s%".equals(s1);
        assert "s%".equals(StringUtil.repeat("s%",1));
        assert "".equals(StringUtil.repeat("s%",0));
        assert "".equals(StringUtil.repeat("",5));
        assert "AA,AA,AA,AA,AA".equals(StringUtil.repeat("AA",5,","));
    }

    @Test
    public void joinTest(){
        String join = StringUtil.join(",", "{","}",1, 'C', "lov");
        assert "{1,C,lov}".equals(join);
    }
}
