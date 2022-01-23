package io.github.wrhcute.utils;

import org.junit.Test;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName StrUtilTest.java
 * @Description TODO
 * @createTime 2022年01月06日 18:11:00
 */
public class StrUtilTest {


    @Test
    public void repeatTest(){
        String s1 = StrUtil.repeat("s%",5);
        System.out.println("repeat(\"s%\",5) =" + s1);
        assert "s%s%s%s%s%".equals(s1);
        assert "s%".equals(StrUtil.repeat("s%",1));
        assert "".equals(StrUtil.repeat("s%",0));
        assert "".equals(StrUtil.repeat("",5));
        assert "AA,AA,AA,AA,AA".equals(StrUtil.repeat("AA",5,","));
    }

    @Test
    public void joinTest(){
        String join = StrUtil.join(",", "{","}",1, 'C', "lov");
        assert "{1,C,lov}".equals(join);
    }


    @Test
    public void formatTest(){

        System.out.println(StrUtil.format("ABC{}E", "D", "E", "F"));
        System.out.println(StrUtil.format("ABC{}{}{}G", "D", "E", "F"));
        System.out.println(StrUtil.format("ABC{}{}{}F", "D", "E"));
        System.out.println(StrUtil.format("ABCF"));
    }


    @Test
    public void paddingTest(){
        System.out.println(StrUtil.padding("999","00",4,true));
    }



    @Test
    public void underline2HumpTest(){
        String str = "get_name";
        assert "getName".equals(StrUtil.underline2Hump(str));
    }
}
