package io.github.wrhcute.utils;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName NumID.java
 * @Description id生成器
 *
 * @createTime 2021年11月17日 15:26:00
 */
public class NumID {

    private final long fixed;
    private volatile long lastTimestamp = System.currentTimeMillis();
    private volatile int seq = 0;
    private static final int MAX_SEQ = 4096; //2的12次方

    public NumID(int fixed) {
        Asserts.isTrue(fixed >= 0 && fixed <= 1024, "fixed必须大于等于0小于等于1024");
        this.fixed = (long) fixed << 53;
    }


    public synchronized long take() {
        for (;;){
            if (System.currentTimeMillis() == lastTimestamp) {
                if (seq >= MAX_SEQ){
                    Thread.yield();
                    continue;
                }
                seq++;
            } else {
                seq = 0;
            }
            return fixed | ((lastTimestamp = System.currentTimeMillis()) << 12) | seq;
        }

    }
}
