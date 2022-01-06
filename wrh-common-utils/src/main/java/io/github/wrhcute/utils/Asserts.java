package io.github.wrhcute.utils;

import java.util.function.Supplier;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName Asserts.java
 * @Description 断言
 * @createTime 2021年12月17日 16:11:00
 */
public abstract class Asserts {


    public static void isFalse(boolean expression, Supplier<Throwable> supplier){
        isTrue(!expression,supplier);
    }

    public static void isFalse(boolean expression, String message,Object ... fmtArgs){
        isTrue(!expression,message,fmtArgs);
    }

    public static void notNull(Object obj , String message,Object ... fmtArgs){
        isTrue(obj != null , message ,fmtArgs);
    }

    public static void notNullParam(Object param,String ... paramNames){
        notNull(param,Threads.getCallMethodName() +
                "调用,参数["+StringUtil.repeat("%s", paramNames.length,",")+"]不能为null", (Object) paramNames);
    }

    public static void isNull(Object obj , String message){
        isTrue(obj == null , message);
    }

    public static void isTrue(boolean expression,String message,Object ... fmtArgs){
        isTrue(expression , new RuntimeException(String.format(message,fmtArgs)));
    }

    public static void isTrue(boolean expression,Throwable ex){
        isTrue(expression,() -> ex);
    }


    public static void isTrue(boolean expression, Supplier<Throwable> supplier){
        if (!expression){
            throw ExceptionUtil.toRuntime(supplier.get());
        }
    }
}
