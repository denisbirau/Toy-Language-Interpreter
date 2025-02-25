package model.adts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary <K, V> implements MyIDictionary<K, V> {
    private final ConcurrentHashMap<K, V> hashMap;

    public MyDictionary() {
        this.hashMap = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        return this.hashMap.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return this.hashMap.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        this.hashMap.put(key, value);
    }

    @Override
    public void remove(K key, V value) {
        this.hashMap.remove(key, value);
    }

    @Override
    public Collection<V> values() {
        return this.hashMap.values();
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyDictionary<K, V> newDict = new MyDictionary<>();
        for (K key : this.hashMap.keySet()) {
            newDict.put(key, this.hashMap.get(key));
        }
        return newDict;
    }

    @Override
    public Map<K, V> getMap() {
        return this.hashMap;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (K key : this.hashMap.keySet()) {
            string.append(key.toString()).append("->").append(this.hashMap.get(key)).append("\n");
        }
        return string.toString();
    }
}
