package io.github.wrhcute.utils;

public abstract class Bits {


    public static int hashMod(Object obj, int mod) {
        Asserts.isTrue(isPowerOf2(mod), new IllegalArgumentException("mod 必须为 2的n次方数"));
        return messyHash(obj) & (mod - 1);
    }

    //传入数值是否为2的幂次方数
    public static boolean isPowerOf2(int num) {
        int abs = Math.abs(num);
        return abs != 1 && (abs & (abs - 1)) == 0;
    }

    //获得一个更为松散的hash值
    public static int messyHash(Object obj){
        int h ;
        return obj == null ? 0 : (h = obj.hashCode()) ^ (h >>> 16);
    }

}
