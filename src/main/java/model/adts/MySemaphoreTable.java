package model.adts;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MySemaphoreTable<T> implements MyISemaphoreTable<T>{
    private final AtomicInteger freeLocation;
    private Map<Integer, T> hashMap;

    public MySemaphoreTable() {
        this.freeLocation = new AtomicInteger(0);
        this.hashMap = new ConcurrentHashMap<>();
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
    public synchronized void put(int address, T value) {
        this.hashMap.put(this.freeLocation.get(), value);
    }

    @Override
    public synchronized void setMap(Map<Integer, T> map) {
        this.hashMap = map;
    }

    @Override
    public synchronized Map<Integer, T> getMap() {
        return this.hashMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer i: this.hashMap.keySet())
            sb.append(i).append("->").append(this.hashMap.get(i)).append("\n");
        return sb.toString();
    }
}
