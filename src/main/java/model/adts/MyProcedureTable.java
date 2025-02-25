package model.adts;

import java.util.HashMap;
import java.util.Map;

public class MyProcedureTable<String, T> implements MyIProcedureTable<String, T>{
    private Map<String, T> hashMap;

    public MyProcedureTable() {
        this.hashMap = new HashMap<>();
    }

    @Override
    public boolean containsKey(String key) {
        return this.hashMap.containsKey(key);
    }

    @Override
    public T get(String key) {
        return this.hashMap.get(key);
    }

    @Override
    public void put(String name, T value) {
        this.hashMap.put(name, value);
    }

    @Override
    public void setMap(Map<String, T> map) {
        this.hashMap = map;
    }

    @Override
    public Map<String, T> getMap() {
        return this.hashMap;
    }

    @Override
    public java.lang.String toString() {
        StringBuilder sb = new StringBuilder();
        for (String name: this.hashMap.keySet()) {
            sb.append(name).append("->").append(this.hashMap.get(name));
        }
        return sb.toString();
    }
}
