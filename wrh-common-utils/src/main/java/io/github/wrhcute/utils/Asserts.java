package io.github.wrhcute.utils;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName Asserts.java
 * @Description 断言
 * @createTime 2021年12月17日 16:11:00
 */
public abstract class Asserts {

    public static void isTrue(boolean expression, Throwable ex){
        if (!expression)
            throw ExceptionUtil.toRuntime(ex);
    }

    public static void isFalse(boolean expression, Throwable ex){
        isTrue(!expression,ex);
    }

    public static void notNull(Object obj , String message){
        isTrue(obj != null , new RuntimeException(message));
    }

    public static void notNullParam(Object param){
        notNull(param,Threads.getCallMethodName() + "调用,参数不能为null");
    }

    public static void isNull(Object obj , String message){
        isTrue(obj == null , new RuntimeException(message));
    }

    public static void isTrue(boolean expression,String message){
        isTrue(expression,new RuntimeException(message));
    }
}
