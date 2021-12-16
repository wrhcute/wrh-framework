package io.github.wrhcute.utils;

import sun.misc.Unsafe;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName UnsafeUtil.java
 * @Description unsafe工具类
 * @createTime 2021年12月16日 10:15:00
 */
public final class UnsafeUtil {

    // 单例对象
    private static final Unsafe theUnsafe = ReflectionUtil.getDeclaredStaticFieldValue(Unsafe.class,"theUnsafe");

    static Unsafe getInstance(){
        return theUnsafe;
    }

}
