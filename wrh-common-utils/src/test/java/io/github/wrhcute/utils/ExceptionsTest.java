package io.github.wrhcute.utils;

import org.junit.Test;

public class ExceptionsTest {


    @Test
    public void stack2StrTest(){
        System.err.println(Exceptions.stack2Str(new RuntimeException("123")));
    }

    @Test
    public void rootCauseTest(){
        Throwable exception = new RuntimeException(new RuntimeException(new RuntimeException("123")));
        System.out.println(Exceptions.stack2Str(Exceptions.rootCause(exception)));
    }
}
