package io.github.wrhcute.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ManyKeyMap<SK,V> implements Map<ManyKeyMap.ManyKey<SK>,V> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
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

    }
}
