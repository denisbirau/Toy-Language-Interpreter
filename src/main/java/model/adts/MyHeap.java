package model.adts;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<T> implements MyIHeap<T>{
    private Map<Integer, T> hashMap;
    private AtomicInteger freeLocation;

    public MyHeap() {
        this.hashMap = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public int allocate(T newValue) {
        this.hashMap.put(this.freeLocation.incrementAndGet(), newValue);
        return this.freeLocation.get();
    }

    @Override
    public boolean containsKey(Integer key) {
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
    public void setHeap(Map<Integer, T> map) {
        this.hashMap = map;
    }

    @Override
    public Map<Integer, T> getMap() {
        return this.hashMap;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Integer key : this.hashMap.keySet()) {
            string.append(key.toString()).append("->").append(this.hashMap.get(key)).append("\n");
        }
        return string.toString();
    }
}
