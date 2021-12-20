package io.github.wrhcute.utils;

import org.junit.Test;

public class BitsTest {

    @Test
    public void isPowerOf2_test(){
        assert !Bits.isPowerOf2(1);
        assert Bits.isPowerOf2(2);
        assert Bits.isPowerOf2(16);
        assert !Bits.isPowerOf2(6);
        assert !Bits.isPowerOf2(17);
        assert Bits.isPowerOf2(256);
        assert Bits.isPowerOf2(-256);
    }

    @Test
    public void hashMod_test(){
        System.out.println(Bits.hashMod(0,16));
        System.out.println(Bits.hashMod(1,16));
        System.out.println(Bits.hashMod(2,16));
        System.out.println(Bits.hashMod(3,16));
        System.out.println(Bits.hashMod(4,16));
        System.out.println(Bits.hashMod(5,16));
        System.out.println("-----------------------------");
        System.out.println(Bits.hashMod(17,16));
        System.out.println(Bits.hashMod(18,16));
        System.out.println(Bits.hashMod(19,16));
        System.out.println(Bits.hashMod(20,16));
        System.out.println(Bits.hashMod(21,16));
        System.out.println(Bits.hashMod(22,16));
        System.out.println(Bits.hashMod(23,16));
        System.out.println(Bits.hashMod(24,16));
        System.out.println(Bits.hashMod(25,16));
        System.out.println("-----------------------------");
        System.out.println(Bits.hashMod("1",16));
        System.out.println(Bits.hashMod(new Object(),16));
        System.out.println(Bits.hashMod(new Object(){public final int a = 1;},16));
    }

    @Test
    public void messyHash_test(){
        System.out.println(Bits.messyHash("100100000001L"));
    }
}
