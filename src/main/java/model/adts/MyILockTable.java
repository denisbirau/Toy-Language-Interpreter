package model.adts;

import java.util.Map;

public interface MyILockTable<T> {
    int allocate(T newValue);
    boolean containsKey(Integer key);
    T get(Integer key);
    void put(int address, T value);
    Map<Integer, T> getMap();
}
