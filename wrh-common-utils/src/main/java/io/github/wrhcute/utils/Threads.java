package io.github.wrhcute.utils;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName Threads.java
 * @Description 线程工具类
 * @createTime 2021年11月26日 14:43:00
 */
public abstract class Threads {

    //调用此方法,获取当前调用方法的调用方法
    public static String getCallMethodName(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length < 4)
            return "main";
        return elements[3].getClassName()+"#" + elements[3].getMethodName();
    }

    public static void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler){
        Thread.currentThread().setUncaughtExceptionHandler(handler);
    }


    public static void main(String[] args) {
        test();
    }

    public static void test(){
        System.out.println(getCallMethodName());
    }
}
