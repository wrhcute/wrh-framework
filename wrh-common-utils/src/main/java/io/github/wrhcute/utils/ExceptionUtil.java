package io.github.wrhcute.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName ExceptionUtil.java
 * @Description 异常工具类
 * @createTime 2021年12月16日 10:14:00
 */
public class ExceptionUtil {

    public static RuntimeException toRuntime(Throwable ex){
        return ex instanceof RuntimeException ? ((RuntimeException) ex) : new RuntimeException(ex);
    }

    public static String stack2Str(Throwable ex){
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw,true));
        return sw.toString();
    }

    public static Throwable rootCause(Throwable ex){
        Throwable rootCause = ex;
        while (rootCause.getCause()!= null){
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
