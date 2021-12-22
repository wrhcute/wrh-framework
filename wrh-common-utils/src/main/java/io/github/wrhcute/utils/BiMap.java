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

    private final Set<Entry<L, R>> entrySet = new HashSet<>();
    private final Set<L> keys = new HashSet<>();
    private final Set<R> values = new HashSet<>();


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
        return lget((L) key);
    }

    public R lget(L l) {
        Entry<L, R> entry = search(l2rBucket, l);
        return entry == null ? null : entry.getValue();
    }

    public L rget(R r) {
        Entry<L, R> entry = search(r2lBucket, r);
        return entry == null ? null : entry.getKey();
    }

    @Override
    public R put(L l, R r) {
        return lput(l,r);
    }

    public R lput(L l,R r){
        Entry<L,R> entry = new SimpleEntry(l,r);
        Entry<L, R> covered = lput0(entry);
        rput0(entry);
        keys.add(entry.getKey());
        values.add(entry.getValue());
        entrySet.add(entry);
        return covered != null ? covered.getValue() : null;
    }

    public L rput(R r,L l){
        Entry<L,R> entry = new SimpleEntry(l,r);
        Entry<L, R> covered = rput0(entry);
        lput0(entry);
        keys.add(entry.getKey());
        values.add(entry.getValue());
        entrySet.add(entry);
        return covered != null ? covered.getKey() : null;
    }

    @Override
    public R remove(Object key) {
        return ldel((L) key);
    }

    public R ldel(L l) {
        Entry<L, R> removed = del0(l2rBucket, r2lBucket, this, l);
        return removed == null ? null : removed.getValue();
    }

    public L rdel(R r) {
        Entry<L, R> removed = del0(r2lBucket, l2rBucket, this, r);
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


    private static <S extends Comparable<S>, K, V> Entry<K, V> search(BiSortTree<S, Entry<K, V>>[] bucket, S s) {
        int mod = Bits.hashMod(s, bucket.length);
        BiSortTree<S, Entry<K, V>> root = bucket[mod];
        return root != null ? root.search(s) : null;
    }

    private static <S1 extends Comparable<S1>
            , S2 extends Comparable<S2>
            , K extends Comparable<K>
            , V extends Comparable<V>> Entry<K, V> del0(
            BiSortTree<S1, Entry<K, V>>[] bucket,
            BiSortTree<S2, Entry<K, V>>[] opposite,
            BiMap<K, V> $this,
            S1 s) {
        int mod = Bits.hashMod(s, bucket.length);
        BiSortTree<S1, Entry<K, V>> root = bucket[mod];
        if (root == null)
            return null;
        Entry<K, V> entry;
        if (root.getS().equals(s)) {
            //刚好在头结点
            bucket[mod] = null;
            entry = root.getData();
        } else {
            entry = root.remove(s);
        }
        if (entry != null) {
            $this.keys.remove(entry.getKey());
            $this.values.remove(entry.getValue());
            $this.entrySet.remove(entry);
            del0(opposite, bucket, $this, (S2) entry.getValue());
        }
        return entry;
    }

    private Entry<L, R> lput0(Entry<L, R> newEntry){
        int mod = Bits.hashMod(newEntry.getKey(), capacity);
        BiSortTree<L, Entry<L, R>> root = l2rBucket[mod];
        if (root == null) {
            root = new BiSortTree<>(newEntry.getKey(), newEntry);
            l2rBucket[mod] = root;
            if (this.values.contains(newEntry.getValue())){
                del0(r2lBucket,l2rBucket,this, newEntry.getValue());
            }
        }else{
            Entry<L, R> old = root.put(newEntry.getKey(), newEntry);
            if (old != null) {
                this.entrySet.remove(old);
                this.values.remove(old.getValue());
                del0(r2lBucket, l2rBucket, this, old.getValue());
            }
            return old;
        }
        return null;
    }

    private Entry<L, R> rput0(Entry<L, R> newEntry){
        int mod = Bits.hashMod(newEntry.getValue(), capacity);
        BiSortTree<R, Entry<L, R>> root = r2lBucket[mod];
        if (root == null) {
            root = new BiSortTree<>(newEntry.getValue(), newEntry);
            r2lBucket[mod] = root;
            if (this.keys.contains(newEntry.getKey())){
                del0(l2rBucket,r2lBucket,this, newEntry.getKey());
            }
        }else{
            Entry<L, R> old = root.put(newEntry.getValue(), newEntry);
            if (old != null) {
                this.entrySet.remove(old);
                this.keys.remove(old.getKey());
                del0(l2rBucket, r2lBucket, this, old.getKey());
            }
            return old;
        }
        return null;
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
