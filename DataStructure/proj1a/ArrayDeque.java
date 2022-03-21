public class ArrayDeque <T> {
    
    private T[] datas;
    private int first;
    private int tail;
    private final static int default_size = 8;
    private final static int ssth_size = 16;
    private final static double rate = 0.25;

    public ArrayDeque() {
        datas = (T[])new Object[default_size];
        tail = default_size / 2;
        first = tail - 1;
    }

    public void addFirst(T data) {
        checkCongestion();
        datas[first--] = data;
    }

    public void addLast(T data) {
        checkCongestion();
        datas[tail++] = data;
    }

    public T removeFirst() {
        if (isEmpty())
            throw new IndexOutOfBoundsException();
        checkCongestion();
        T data = datas[first++];
        datas[first] = null;
        return data;
    }

    public T removeLast() {
        if (isEmpty())
            throw new IndexOutOfBoundsException();
        checkCongestion();
        T data = datas[tail--];
        datas[tail] = null;
        return data;
    }

    private void checkCongestion() {
        if (first == -1 || tail == datas.length)
            resize(true);
        else if (datas.length > ssth_size && ((double) size() / datas.length) - rate < 0.01)
            resize(false);
        
    }

    private void resize(boolean greater) {
        T[] newDatas;
        if (greater)
            newDatas = (T[])new Object[datas.length << 1];
        else
            newDatas = (T[])new Object[datas.length >> 1];
        int restSize = newDatas.length - size();
        int newFirst = restSize / 2 - 1;
        int newTail = newFirst + size() + 1;
        for (int i = newFirst + 1, j = first + 1; i < newTail; i++, j++) {
            newDatas[i] = datas[j];
        }
        first = newFirst;
        tail = newTail;
        datas = newDatas;
    }

    /*
    private void extend() {
        T[] newDatas = (T[])new Object[datas.length << 1];
        int t = datas.length / 2;
        int f = t - 1;
        int new_tail = newDatas.length / 2;
        int new_first = new_tail - 1;
        while (t < tail) {
            newDatas[new_tail++] = datas[t++];
        }
        while (f > first) {
            newDatas[new_first--] = datas[f--];
        }
        tail = new_tail;
        first = new_first;
        datas = newDatas;
    }
    */

    public T get(int index) {
        if (index <= first || index >= tail) {
            throw new IndexOutOfBoundsException();
        }
        return datas[index];
    }

    public void printDeque() {

        for (int i = first + 1; i < tail; i++) {
            System.out.print(datas[i] + " ");
        }
    }

    public int size() {
        return tail - first - 1;
    }

    public boolean isEmpty() {
        return tail - first - 1 == 0;
    }
}
