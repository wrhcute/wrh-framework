package io.github.wrhcute.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName SlidingTimeWindow.java
 * @Description 滑动时间窗口
 * @createTime 2021年12月15日 14:51:00
 */
public class SlidingTimeWindow {


    private final AtomicReferenceArray<Slot> slots;
    private final long intervalMs;
    private final int windowLen;
    private final long gap;

    public SlidingTimeWindow(long intervalMs , int windowLen){
        if (intervalMs % windowLen != 0)
            throw new IllegalArgumentException("intervalMs 必须被 windowLen 整除!");
        this.intervalMs = intervalMs;
        this.windowLen = windowLen;
        this.gap = intervalMs / windowLen ;
        slots = new AtomicReferenceArray<>(new Slot[windowLen]);
    }

    public void incrBy(int increment){
        int index = calculateTimeIdx();
        Slot slot = slots.get(index);
        if (slot == null){
            slot = Slot.newSlot(calculateWindowStart());
            slot.counter.addAndGet(increment);
            if(!slots.compareAndSet(index,null,slot)){
                Thread.yield();
                incrBy(increment);
            }
        }else if (slot.startTimestamp + gap < System.currentTimeMillis()){//窗口滑动
            //  TODO 有并发问题
            Slot newSlot = Slot.newSlot(calculateWindowStart());
            slots.compareAndSet(index,slot,newSlot);
            newSlot.counter.addAndGet(increment);
        }else{
            slot.counter.addAndGet(increment);
        }
    }

    public int currentCount(){
        Slot slot = slots.get(calculateTimeIdx());
        return slot == null ? 0 : slot.counter.get();
    }

    public int currentWindowCount(){
        int c = 0;
        for (int i = 0; i < slots.length();i ++){
            Slot slot = slots.get(i);
            c += slot == null ? 0 : slot.counter.get();
        }
        return c;
    }

/*    private int calcTimeIndex(){
        return (int) (System.currentTimeMillis() % intervalMs  /gap);
    }*/

    private int calculateTimeIdx() {
        long timeId = System.currentTimeMillis() / gap;
        return (int)(timeId % windowLen);
    }

    protected long calculateWindowStart() {
        return System.currentTimeMillis() - System.currentTimeMillis() % gap;
    }

    private static class Slot {
        long startTimestamp;

        AtomicInteger counter = new AtomicInteger(0);

        static Slot newSlot(long start){
            Slot slot = new Slot();
            slot.startTimestamp = start;
            return slot;
        }
    }
}
