package io.github.wrhcute.utils;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName DistinctList.java
 * @Description 忽略重复元素的List
 * @createTime 2021年12月16日 14:35:00
 */
public class DistinctList<E> extends java.util.ArrayList<E>{

    @Override
    public boolean add(E e) {
        return !contains(e) && super.add(e);
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        return super.addAll(distinct(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return super.addAll(index,distinct(c));
    }

    @Override
    public E set(int index, E element) {
        if (contains(element))
            return null;
        return super.set(index,element);
    }

    private Collection<? extends E> distinct(Collection<? extends E> c){
        return c instanceof HashSet ? c : new HashSet<>(c);
    }

}
