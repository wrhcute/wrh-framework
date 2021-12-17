package io.github.wrhcute.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName InverseMap.java
 * @Description
 * @createTime 2021年12月17日 17:30:00
 */
public class InverseMap<K,V> implements Map<K,V>{

    private final HashMap<K,V> hashMap = new HashMap<>();
    private final HashMap<V,K> inverseMap = new HashMap<>();

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return hashMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return hashMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return hashMap.get(key);
    }

    public K inverseGet(V v){
        return inverseMap.get(v);
    }

    @Override
    public V put(K key, V value) {
        Asserts.notNullParam(key);
        Asserts.notNullParam(value);
        inverseMap.put(value,key);
        return hashMap.put(key,value);
    }

    @Override
    public V remove(Object key) {
        V remove = hashMap.remove(key);
        if (remove != null){
            inverseMap.remove(remove);
        }
        return remove;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        hashMap.putAll(m);
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            inverseMap.put(entry.getValue(),entry.getKey());
        }
    }

    @Override
    public void clear() {
        hashMap.clear();
        inverseMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return hashMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return hashMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return hashMap.entrySet();
    }

    public Map<V,K> inverse(){
        return inverseMap;
    }

    @Override
    public String toString() {
        return "InverseMap"+hashMap.entrySet();
    }
}
