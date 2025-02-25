package model.adts;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLockTable<T> implements MyILockTable<T>{
    private final ConcurrentMap<Integer, T> hashMap;
    private final AtomicInteger freeLocation;

    public MyLockTable() {
        this.hashMap = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public synchronized int allocate(T newValue) {
        this.hashMap.put(this.freeLocation.incrementAndGet(), newValue);
        return this.freeLocation.get();
    }

    @Override
    public synchronized boolean containsKey(Integer key) {
        return this.hashMap.containsKey(key);
    }

    @Override
    public T get(Integer key) {
        return this.hashMap.get(key);
    }

    @Override
    public void put(int address, T value) {
        this.hashMap.put(address, value);
    }

    @Override
    public Map<Integer, T> getMap() {
        return this.hashMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer key: this.hashMap.keySet())
            sb.append(key).append("->").append(this.hashMap.get(key)).append("\n");
        return sb.toString();
    }
}
