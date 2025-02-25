package model.adts;

import java.util.Map;

public interface MyISemaphoreTable<T> {
    int allocate(T newValue);
    boolean containsKey(Integer key);
    T get(Integer key);
    void put(int address, T value);
    void setMap(Map<Integer, T> map);
    Map<Integer, T> getMap();
}
