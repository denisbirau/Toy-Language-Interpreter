package model.adts;

import java.util.Map;

public interface MyIProcedureTable<String, T> {
    boolean containsKey(String key);
    T get(String key);
    void put(String name, T value);
    void setMap(Map<String, T> map);
    Map<String, T> getMap();
}
