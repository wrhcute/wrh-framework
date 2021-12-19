package io.github.wrhcute.utils;

import org.junit.Test;

public class BitsTest {

    @Test
    public void isPowerOf2_test(){
        assert !Bits.isPowerOf2(1);
        assert Bits.isPowerOf2(2);
        assert Bits.isPowerOf2(16);
        assert !Bits.isPowerOf2(17);
        assert Bits.isPowerOf2(256);
    }
}
