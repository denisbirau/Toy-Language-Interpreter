package model.adts;

import java.util.List;

public interface MyIList <T> {
    void add(T item);
    List<T> getList();
}
