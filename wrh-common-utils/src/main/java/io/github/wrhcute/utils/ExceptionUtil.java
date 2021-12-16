package io.github.wrhcute.utils;

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
}
