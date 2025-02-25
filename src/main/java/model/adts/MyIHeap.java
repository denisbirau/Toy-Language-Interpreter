package model.adts;

import java.util.Map;

public interface MyIHeap<T> {
    int allocate(T newValue);
    boolean containsKey(Integer key);
    T get(Integer key);
    void put(int address, T value);
    void setHeap(Map<Integer, T> map);
    Map<Integer, T> getMap();
}
