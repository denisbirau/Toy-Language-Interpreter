package model.adts;

import exceptions.ADTException;
import model.statements.IStatement;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyStack<T> implements MyIStack<T> {
    private final LinkedList<T> linkedList;

    public MyStack() {
        this.linkedList = new LinkedList<>();
    }

    @Override
    public void push(T item) {
        this.linkedList.push(item);
    }

    @Override
    public T pop() throws ADTException {
        try {
            return this.linkedList.pop();
        } catch (NoSuchElementException e) {
            throw new ADTException("Stack is empty!");
        }
    }

    @Override
    public boolean isEmpty() {
        return this.linkedList.isEmpty();
    }

    @Override
    public List<T> getElements() {
        return this.linkedList;
    }

    @Override
    public MyIStack<T> deepCopy() {
        MyIStack<T> newStack = new MyStack<>();
        for (T el: this.linkedList.reversed())
            newStack.push(el);
        return newStack;
    }

    @Override
    public T peek() {
        return this.linkedList.peek();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (T value: this.linkedList) {
            string.append(value).append("\n");
        }
        return string.toString();
    }
}
