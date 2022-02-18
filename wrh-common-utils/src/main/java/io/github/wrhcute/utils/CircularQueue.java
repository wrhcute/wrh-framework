package io.github.wrhcute.utils;

import java.util.AbstractQueue;
import java.util.Iterator;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName CircularQueue.java
 * @Description TODO
 * @createTime 2022年02月01日 15:43:00
 */
public class CircularQueue<E> extends AbstractQueue<E> {

    private E[] data;
    private int cap;
    private int head = 0, tail = 0;

    public CircularQueue(int cap) {
        if (cap <= 1)
            throw new RuntimeException("循环队列cap必需大于1");
        this.cap = cap;
        data = (E[]) new Object[cap];
    }

    public CircularQueue(E[] array) {
        if (array.length <= 1)
            throw new RuntimeException("array.length 必需大于1");
        this.cap = array.length;
        data = array;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return (tail - head + cap) % cap;
    }

    @Override
    public boolean offer(E e) {
        if ((tail + 1) % cap == head)
            return false;
        data[tail] = e;
        tail = (tail + 1) % cap;
        return true;
    }

    @Override
    public E poll() {
        if (head == tail)
            return null;
        E item = data[head];
        data[head] = null;
        head = (head + 1) % cap;
        return item;
    }

    @Override
    public E peek() {
        return head == tail ? null : data[head];
    }
}
