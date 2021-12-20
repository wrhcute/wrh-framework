package io.github.wrhcute.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName BiMap.java
 * @Description 二元map
 * @createTime 2021年12月20日 10:57:00
 */
@SuppressWarnings("unchecked")
public class BiMap<L extends Comparable<L>,R extends Comparable<R>> implements Map<L,R> {

    private int capacity;

    private BiSortTree<L,R>[] l2rBucket;

    private BiSortTree<R,L>[] r2lBucket;

    private Set<Entry<L,R>> entrySet = new HashSet<>();
    private Set<L> keys = new HashSet<>();
    private Set<R> values = new HashSet<>();



    public BiMap(){
        this(256);
    }

    public BiMap(int capacity){
        Asserts.isTrue(Bits.isPowerOf2(capacity),"capacity必需为2的幂次方数");
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
        return leftSearch(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return rightSearch(value) != null;
    }

    @Override
    public R get(Object key) {
        BiSortTree<L, R> node = leftSearch(key);
        return node == null ? null : node.getData();
    }

    public L rget(R r){
        BiSortTree<R, L> node = rightSearch(r);
        return node == null ? null : node.getData();
    }

    @Override
    public R put(L l, R r) {
        int lMod = Bits.hashMod(l,capacity) , rMod = Bits.hashMod(r,capacity);
        BiSortTree<L, R> lrTree = l2rBucket[lMod];
        if (lrTree == null){
            lrTree = new BiSortTree<>(l,r);
            l2rBucket[lMod] = lrTree;
        }else{
            lrTree.put(l,r);
        }
        BiSortTree<R, L> rlTree = r2lBucket[rMod];
        if (rlTree == null){
            rlTree = new BiSortTree<>(r,l);
            r2lBucket[rMod] = rlTree;
        }else{
            rlTree.put(r,l);
        }
        Entry<L,R> entry = new SimpleEntry(l, r);
        entrySet.add(entry);
        keys.add(l);
        values.add(r);
        return r;
    }

    @Override
    public R remove(Object key) {
        L l = (L) key;
        int mod = Bits.hashMod(key, capacity);
        BiSortTree<L, R> root = l2rBucket[mod];
        R r;
        if (root != null){
            if (root.getS().equals(l)){
                l2rBucket[mod] = null;
                r = root.getData();
            }else{
                r = root.remove(l);
                if (r != null){
                    int heMod = Bits.hashMod(r, capacity);
                    BiSortTree<R, L> he = r2lBucket[heMod];
                    if (he != null){
                        if (he.getS().equals(r)) {
                            r2lBucket[heMod] = null;
                        }else{
                            he.remove(r);
                        }
                    }
                    values.remove(r);
                }
            }
        }else{
            r = null;
        }
        keys.remove(l);
        return r;
    }

    @Override
    public void putAll(Map<? extends L, ? extends R> m) {
        for (L l : m.keySet()) {
            R r = m.get(l);
            put(l,r);
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


    private BiSortTree<L, R> leftSearch(Object left){
        int mod = Bits.hashMod(left, capacity);
        BiSortTree<L, R> tree = l2rBucket[mod];
        return tree.searchTree((L) left);
    }


    private BiSortTree<R, L> rightSearch(Object right){
        int mod = Bits.hashMod(right, capacity);
        BiSortTree<R, L> tree = r2lBucket[mod];
        return tree.searchTree((R) right);
    }


    public  class SimpleEntry implements Entry<L, R> {

        SimpleEntry(L key ,R value){
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

    }
}
