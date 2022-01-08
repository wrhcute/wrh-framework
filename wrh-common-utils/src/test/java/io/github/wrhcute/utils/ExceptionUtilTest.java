package io.github.wrhcute.utils;

import org.junit.Test;

public class ExceptionUtilTest {


    @Test
    public void stack2StrTest(){
        System.err.println(ExceptionUtil.stack2Str(new RuntimeException("123")));
    }

    @Test
    public void rootCauseTest(){
        Throwable exception = new RuntimeException(new RuntimeException(new RuntimeException("123")));
        System.out.println(ExceptionUtil.stack2Str(ExceptionUtil.rootCause(exception)));
    }
}
