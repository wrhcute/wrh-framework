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
        List<Entry<L, R>> entries = lput(l, r);
        return entries.isEmpty() ? null : entries.get(0).getValue();
    }

    public List<Entry<L, R>> lput(L l, R r) {
        List<Entry<L, R>> covered = Colls.fit(ldel(l), rdel(r));
        Entry<L, R> entry = new SimpleEntry(l, r);
        lput0(entry);
        rput0(entry);
        keys.add(entry.getKey());
        values.add(entry.getValue());
        entrySet.add(entry);
        return covered;
    }

    @Override
    public R remove(Object key) {
        List<Entry<L, R>> del = ldel((L) key);
        return del.isEmpty() ? null : del.get(0).getValue();
    }

    public List<Entry<L, R>> ldel(L l) {
        Entry<L, R> lrm = ldel0(l), rrm;
        if (lrm != null)
            rrm = rdel0(lrm.getValue());
        else
            rrm = null;
        return removedEntries(lrm, rrm);
    }

    public List<Entry<L, R>> rdel(R r) {
        Entry<L, R> rrm = rdel0(r), lrm;
        if (rrm != null) {
            lrm = ldel0(rrm.getKey());
        } else {
            lrm = null;
        }
        return removedEntries(rrm, lrm);
    }


    private List<Entry<L, R>> removedEntries(Entry<L, R> first, Entry<L, R> second) {
        List<Entry<L, R>> rs = new ArrayList<>();
        if (first == second && first != null){
            rs.add(first);
        }else{
            if (first != null)
                rs.add(first);
            if (second != null)
                rs.add(second);
        }
        return rs;
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

    private Entry<L, R> ldel0(L l) {
        int mod = Bits.hashMod(l, l2rBucket.length);
        BiSortTree<L, Entry<L, R>> tree = l2rBucket[mod];
        if (tree == null) {
            return null;
        } else {
            Entry<L, R> remove;
            if (tree.getRoot().getS().equals(l)) {
                remove = tree.getRoot().getData();
                l2rBucket[mod] = null;
            } else {
                remove = tree.remove(l);
            }
            if (remove != null) {
                keys.remove(l);
                entrySet.remove(remove);
            }
            return remove;
        }
    }

    private Entry<L, R> rdel0(R r) {
        int mod = Bits.hashMod(r, r2lBucket.length);
        BiSortTree<R, Entry<L, R>> root = r2lBucket[mod];
        if (root == null) {
            return null;
        } else {
            Entry<L, R> remove;
            if (root.getRoot().getS().equals(r)) {
                remove = root.getRoot().getData();
                r2lBucket[mod] = null;
            } else {
                remove = root.remove(r);
            }
            if (remove != null) {
                values.remove(r);
                entrySet.remove(remove);
            }
            return remove;
        }
    }

    private void lput0(Entry<L, R> newEntry) {
        int mod = Bits.hashMod(newEntry.getKey(), capacity);
        BiSortTree<L, Entry<L, R>> root;
        if (l2rBucket[mod] == null) {
            root = new BiSortTree<>(newEntry.getKey(), newEntry);
            l2rBucket[mod] = root;
        } else {
            root = l2rBucket[mod];
            root.put(newEntry.getKey(), newEntry);
        }
    }

    private void rput0(Entry<L, R> newEntry) {
        int mod = Bits.hashMod(newEntry.getValue(), capacity);
        BiSortTree<R, Entry<L, R>> root;
        if (r2lBucket[mod] == null) {
            root = new BiSortTree<>(newEntry.getValue(), newEntry);
            r2lBucket[mod] = root;
        } else {
            root = r2lBucket[mod];
            root.put(newEntry.getValue(), newEntry);
        }
    }


    public class SimpleEntry implements Entry<L, R> {

        SimpleEntry(L l, R r) {
            this.l = l;
            this.r = r;
        }

        private L l;
        private R r;

        @Override
        public L getKey() {
            return l;
        }

        @Override
        public R getValue() {
            return r;
        }

        @Override
        public R setValue(R value) {
            R old = this.r;
            this.r = value;
            return old;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleEntry that = (SimpleEntry) o;
            return Objects.equals(l, that.l) && Objects.equals(r, that.r);
        }

        @Override
        public int hashCode() {
            return Objects.hash(l, r);
        }

        @Override
        public String toString() {
            return "BiMap.SimpleEntry{" +
                    "L=" + l +
                    ", R=" + r +
                    '}';
        }
    }
}
