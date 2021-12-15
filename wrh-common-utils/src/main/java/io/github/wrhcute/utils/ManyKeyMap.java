package io.github.wrhcute.utils;

import java.util.*;

public class ManyKeyMap<SK,V> implements Map<ManyKeyMap.ManyKey<SK>,V> {

    private List<V> values = new ArrayList<>();

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
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(ManyKey<SK> key, V value) {
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
        private List<SK> keys = new ArrayList<>();


    }
}
