package model.adts;

import java.util.List;
import java.util.Vector;

public class MyList<T> implements MyIList<T> {
    private final Vector<T> arrayList;

    public MyList() {
        this.arrayList = new Vector<>();
    }

    @Override
    public void add(T item) {
        this.arrayList.add(item);
    }

    @Override
    public List<T> getList() {
        return arrayList;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (T value: this.arrayList) {
            string.append(value).append("\n");
        }
        return string.toString();
    }
}
