package io.github.wrhcute.utils;

import java.util.*;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName BiMap.java
 * @Description 二元map
 * @createTime 2021年12月20日 10:57:00
 */
@SuppressWarnings("unchecked")
public class BiMap<L extends Comparable<L>, R extends Comparable<R>> implements Map<L, R> {

    private int capacity;

    private BiSortTree<L, Entry<L, R>>[] l2rBucket;

    private BiSortTree<R, Entry<L, R>>[] r2lBucket;

    private Set<Entry<L, R>> entrySet = new HashSet<>();
    private Set<L> keys = new HashSet<>();
    private Set<R> values = new HashSet<>();


    public BiMap() {
        this(256);
    }

    public BiMap(int capacity) {
        Asserts.isTrue(Bits.isPowerOf2(capacity), "capacity必需为2的幂次方数");
        this.capacity = capacity;
        this.l2rBucket = new BiSortTree[capacity];
        this.r2lBucket = new BiSortTree[capacity];
    }

    @Override
    public int size() {
        return entrySet.size();
    }

    @Override
    public boolean isEmpty() {
        return entrySet.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public R get(Object key) {
        Entry<L, R> entry = search(l2rBucket, (L) key);
        return entry == null ? null : entry.getValue();
    }

    public L rget(R r) {
        Entry<L, R> entry = search(r2lBucket, r);
        return entry == null ? null : entry.getKey();
    }

    @Override
    public R put(L l, R r) {
        int lMod = Bits.hashMod(l, capacity), rMod = Bits.hashMod(r, capacity);
        BiSortTree<L, Entry<L, R>> lrTree = l2rBucket[lMod];
        Entry<L, R> newEntry = new SimpleEntry(l, r);
        if (lrTree == null) {
            lrTree = new BiSortTree<>(l, newEntry);
            l2rBucket[lMod] = lrTree;
        } else {
            Entry<L, R> old = lrTree.put(l, newEntry);
            if (old != null) {
                entrySet.remove(old);
            }
        }
        BiSortTree<R, Entry<L, R>> rlTree = r2lBucket[rMod];
        if (rlTree == null) {
            rlTree = new BiSortTree<>(r, newEntry);
            r2lBucket[rMod] = rlTree;
        } else {
            Entry<L, R> old = rlTree.put(r, newEntry);
            if (old != null) {
                entrySet.remove(old);
                if (!old.getKey().equals(l)){
                    //todo

                }
            }
        }
        entrySet.add(newEntry);
        keys.add(l);
        values.add(r);
        return r;
    }

    @Override
    public R remove(Object key) {
        Entry<L, R> removed = remove0(l2rBucket, r2lBucket, this,(L) key);
        return removed == null ? null : removed.getValue();
    }

    public L rdel(R r){
        Entry<L, R> removed = remove0(r2lBucket, l2rBucket,this, r);
        return removed == null ? null : removed.getKey();
    }

    @Override
    public void putAll(Map<? extends L, ? extends R> m) {
        for (L l : m.keySet()) {
            R r = m.get(l);
            put(l, r);
        }
    }

    @Override
    public void clear() {
        entrySet.clear();
        keys.clear();
        values.clear();
        l2rBucket = new BiSortTree[capacity];
        r2lBucket = new BiSortTree[capacity];
    }

    @Override
    public Set<L> keySet() {
        return keys;
    }

    @Override
    public Collection<R> values() {
        return values;
    }

    @Override
    public Set<Entry<L, R>> entrySet() {
        return entrySet;
    }


    private static <S extends Comparable<S>,K,V> Entry<K,V> search(BiSortTree<S, Entry<K, V>>[] bucket,S s){
        int mod = Bits.hashMod(s, bucket.length);
        BiSortTree<S, Entry<K, V>> root = bucket[mod];
        return root.search(s);
    }

    private static <S1 extends Comparable<S1>
            ,S2 extends Comparable<S2>
            ,K extends Comparable<K>
            ,V extends Comparable<V>> Entry<K,V> remove0(
            BiSortTree<S1, Entry<K, V>>[] bucket,
            BiSortTree<S2, Entry<K, V>>[] opposite,
            BiMap<K,V> $this,
            S1 s){
        int mod = Bits.hashMod(s, bucket.length);
        BiSortTree<S1, Entry<K, V>> root = bucket[mod];
        if (root == null)
            return null;
        Entry<K,V> entry;
        if (root.getS().equals(s)){
            //刚好在头结点
            bucket[mod] = null;
            entry =  root.getData();
        }else{
            entry = root.remove(s);
        }
        if (entry != null){
            $this.keys.remove(entry.getKey());
            $this.values.remove(entry.getValue());
            $this.entrySet.remove(entry);
            remove0(opposite,bucket,$this,(S2) entry.getValue());
        }
        return entry;
    }


    public class SimpleEntry implements Entry<L, R> {

        SimpleEntry(L key, R value) {
            this.key = key;
            this.value = value;
        }

        private L key;
        private R value;

        @Override
        public L getKey() {
            return key;
        }

        @Override
        public R getValue() {
            return value;
        }

        @Override
        public R setValue(R value) {
            R old = this.value;
            this.value = value;
            return old;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleEntry that = (SimpleEntry) o;
            return Objects.equals(key, that.key) && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
