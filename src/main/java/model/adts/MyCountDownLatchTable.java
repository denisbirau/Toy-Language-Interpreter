package model.adts;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCountDownLatchTable<T> implements MyICountDownLatchTable<T>{
    private final AtomicInteger freeLocation;
    private final Map<Integer, T> latchTable;

    public MyCountDownLatchTable() {
        this.latchTable = new HashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public synchronized int allocate(T value) {
        this.latchTable.put(this.freeLocation.incrementAndGet(), value);
        return this.freeLocation.get();
    }

    @Override
    public synchronized T get(int address) {
        return this.latchTable.get(address);
    }

    @Override
    public void put(int address, T value) {
        this.latchTable.put(address, value);
    }

    @Override
    public boolean containsKey(int address) {
        return this.latchTable.containsKey(address);
    }

    @Override
    public Map<Integer, T> getMap() {
        return this.latchTable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i: this.latchTable.keySet()) {
            sb.append(i).append("->").append(this.latchTable.get(i)).append("\n");
        }
        return sb.toString();
    }
}
