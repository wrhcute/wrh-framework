package io.github.wrhcute.utils;

public abstract class Bits {


    public static int hashMod(Object obj, int mod) {
        Asserts.isTrue(isPowerOf2(mod), new IllegalArgumentException("mod 必须为 2的n次方数"));
        int h;
        return (obj == null) ? 0 : (h = obj.hashCode()) ^ (h >>> 16);
    }

    //传入数值是否为2的幂次方数
    public static boolean isPowerOf2(int num) {
        return num != 1 && (num & (num - 1)) == 0;
    }

}
