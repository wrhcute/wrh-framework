package io.github.wrhcute.utils;

import java.util.Iterator;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName ByteArray.java
 * @Description TODO
 * @createTime 2022年02月28日 17:53:00
 */
public class BitArray implements Iterable<Boolean> {

    private final byte[] data;
    private final long maxOffset;
    //读写位的偏移量
    private long readOffset = 0, writeOffset = 0;

    private static final long $long1 = 0b11111111;

    private static final int $byte2 =  0b10000000;

    private static final char $char2 = 0b1000000000000000;

    private static final int BYTE_WORD_LEN = 8; //byte的字长

    private static final int CHAR_WORD_LEN = 16; //char的字长

    private static final int INT_WORD_LEN = 32; //int的字长

    private static final int LONG_WORD_LEN = 64; //long的字长

    private BitArray(byte[] arr) {
        this.data = arr;
        this.maxOffset = (long) this.data.length * BYTE_WORD_LEN - 1;
    }


    public byte[] toByteArray(){
        return this.data.clone();
    }


    public static BitArray allocate(int byteSize){
        return new BitArray(new byte[byteSize]);
    }

    public void writeBinaryStr(String binaryStr){
        for (char c : binaryStr.toCharArray()) {
            writeBit(c == '1');
        }
    }

    public void writeBit(boolean bit) {
        byte b = getByte(writeOffset);
        int i = bitIndex(writeOffset);
        if (bit) {
            byte w = (byte) (1 << (BYTE_WORD_LEN - 1 - i));
            data[byteIndex(writeOffset)] = (byte) (b | w);
        }
        writeOffset++;
    }

    public void writeChar(char c) {
        for (int i = 0; i < CHAR_WORD_LEN; i++) {
            int k = $char2 >> i;
            writeBit((k & c) == k);
        }
    }



    public char readChar() {
        char c = 0;
        for (int i = 0; i < CHAR_WORD_LEN; i++) {
            if (readBit()){
                int yw = CHAR_WORD_LEN - 1 - i;
                c = (char) (c | (1 << yw));
            }

        }
        return c;
    }

    private byte getByte(long offset) {
        check(offset);
        return data[byteIndex(offset)];
    }

    public boolean readBit() {
        return getBit(readOffset++);
    }

    public boolean getBit(long offset) {
        byte b = getByte(offset);
        int index = bitIndex(offset);
        byte k = (byte) ($byte2 >> index);
        return (k & b) == k;
    }

    private void check(long offset) {
        if (offset >= data.length * 8L) throw new RuntimeException("偏移量已到底");
    }

    private static int byteIndex(long offset) {
        return (int) (offset / BYTE_WORD_LEN);
    }

    private static int bitIndex(long offset) {
        return (int) offset % BYTE_WORD_LEN;
    }

    public static byte[] toByteArr(int data) {
        byte[] arr = initArr(INT_WORD_LEN);
        int c = 0xff;
        arr[0] = (byte) ((c << (32 - 8) & data) >> (32 - 8));
        arr[1] = (byte) ((c << (32 - 16) & data) >> (32 - 16));
        arr[2] = (byte) ((c << (32 - 24) & data) >> (32 - 24));
        arr[3] = (byte) (c  & data);
        return arr;
    }


    public static byte[] toByteArr(long data) {
        byte[] arr = initArr(LONG_WORD_LEN);
        long c = 0xff;
        for (int i = 0; i < arr.length; i++) {
            int yw = 64 - 8 *( i + 1);
            arr[i] = (byte) ((c << yw & data) >> yw);
        }
        return arr;
    }

    private static byte[] initArr(int wordLen) {
        return new byte[wordLen / BYTE_WORD_LEN];
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new Iterator<Boolean>() {

            private int offset = 0;

            @Override
            public boolean hasNext() {
                return offset <= maxOffset;
            }

            @Override
            public Boolean next() {
                return getBit(offset++);
            }
        };
    }
}
