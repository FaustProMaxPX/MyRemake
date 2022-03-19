package list;

import java.util.Arrays;

public class ArrayList<T> implements List<T> {

    // the ratio that used to judge wheather need to extend / shrink array
    private static final double exRate = 0.8;
    private static final double shRate = 0.25;
    // When the array need to be resize, make it's size * or / ratio
    private static final int ratio = 2;
    private static final int defaultSize = 16;

    private T[] datas;
    private int size;

    // AF(datas, size) => A list contains {size} datas, and the datas are in {datas}
    // Rep invariant:
    //      datas != null && size >= 0

    public ArrayList() {
        datas = (T[]) new Object[defaultSize];
        size = 0;
    }

    @Override
    public void addFirst(T data) {
        add(data, 0);
    }

    @Override
    public void addLast(T data) {
        if (checkRate()) {
            resize();
        }
        datas[size++] = data;
    }

    @Override
    public void add(T data, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (checkRate()) {
            resize();
        }
        for (int i = size - 1; i >= index; i--) {
            datas[i + 1] = datas[i];
        }
        datas[index] = data;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return datas[index];
    }

    @Override
    public void set(T data, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } 
        datas[index] = data;
    }

    private boolean checkRate() {
        return (size / datas.length) - exRate >= 0.001;
                    // || (size / datas.length) - 0.25 <= 0.001;
    }

    private void resize() {

        T[] newDatas = Arrays.copyOf(datas, datas.length * ratio);
        datas = newDatas;
    }
}
