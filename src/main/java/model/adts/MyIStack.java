package model.adts;

import exceptions.ADTException;

import java.util.List;

public interface MyIStack<T> {
    void push(T item);
    T pop() throws ADTException;
    boolean isEmpty();
    List<T> getElements();

    MyIStack<T> deepCopy();

    T peek();
}
