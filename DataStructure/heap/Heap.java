public class Heap {

    private Integer[] vals;
    private int size;
    // private int capacity = 16;
    
    public Heap(int N) {
        vals = new Integer[N+1];
        vals[0] = -1;
        size = 0;
    }

    public Heap(Integer[] vals) {
        this(vals.length);
        for (int i = 0; i < vals.length; i++) {
            this.vals[i + 1] = Integer.valueOf(vals[i]);
        }
        size = vals.length;
        for (int parent = size / 2; parent > 0; parent /= 2) {
            precDown(parent);
        }
    }

    private void precDown(int root) {
        int parent, child;
        Integer val = vals[root];
        for (parent = root; parent * 2 <= size; parent = child) {
            child = parent * 2;
            if (child != size && vals[child+1] < vals[child]) {
                child++;
            }
            if (val < vals[child]) {
                break;
            }
            else {
                vals[parent] = vals[child];
            }
        }
        vals[parent] = val;
    }

    public boolean isFull() {
        return size + 1 == vals.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(int val) {
        if (isFull()) {
            throw new IndexOutOfBoundsException("The heap is full");
        }
        int i = ++size;
        // 将空穴上移到首个满足堆条件的位置
        for (; vals[i/2] > val; i /= 2) {
            vals[i] = vals[i/2];
        }
        vals[i] = val;
    }

    public Integer remove() {
        Integer ret = vals[1];
        Integer tmp = vals[size--];
        int child, parent;
        for (parent = 1; parent*2 <= size; parent = child) {
            child = parent * 2;
            if ((child != size) && vals[child] > vals[child+1]) {
                child++;
            }
            if (tmp < vals[child]) {
                break;
            }
            else
                vals[parent] = vals[child];
        }
        vals[parent] = tmp;
        return ret;
    }
 
    
}