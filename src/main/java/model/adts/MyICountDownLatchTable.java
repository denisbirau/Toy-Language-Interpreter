package model.adts;

import java.util.Map;

public interface MyICountDownLatchTable<T> {
    int allocate(T value);

    T get(int address);
    void put(int address, T value);
    boolean containsKey(int address);

    Map<Integer, T> getMap();
}
