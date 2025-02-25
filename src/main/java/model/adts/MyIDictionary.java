package model.adts;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary <K, V> {
    V get(K key);
    boolean containsKey(K key);
    void put(K key, V value);
    void remove(K key, V value);
    Collection<V> values();
    MyIDictionary<K,V> deepCopy();
    Map<K, V> getMap();
}
