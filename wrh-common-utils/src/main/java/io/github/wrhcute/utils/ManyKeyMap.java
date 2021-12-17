package io.github.wrhcute.utils;

import java.util.*;

public class ManyKeyMap<SK,V> implements Map<ManyKeyMap.ManyKey<SK>,V> {

    private final DistinctList<V> values = new DistinctList<>();

    private final Map<SK,Integer> keys = new HashMap<>();

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }

    @Override
    public V get(Object key) {
        Integer vIdx = keys.get(key);
        return  vIdx == null ? null : values.get(vIdx);
    }

    @Override
    public V put(ManyKey<SK> key, V value) {
        if (values.contains(value)){

        }
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends ManyKey<SK>, ? extends V> m) {

    }

    @Override
    public void clear() {
        this.values.clear();
        this.keys.clear();
    }

    @Override
    public Set<ManyKey<SK>> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<ManyKey<SK>, V>> entrySet() {
        return null;
    }

    public static class ManyKey<SK> {
        private TreeSet<SK> keys = new TreeSet<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ManyKey<?> manyKey = (ManyKey<?>) o;
            return Objects.equals(keys, manyKey.keys);
        }

        @Override
        public int hashCode() {
            return Objects.hash(keys);
        }
    }
}
