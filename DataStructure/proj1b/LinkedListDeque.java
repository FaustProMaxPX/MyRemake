
public class LinkedListDeque <T> implements Deque<T>{
    
    class Node {
        T data;
        Node prev;
        Node next;
        Node() {}
        Node(T data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head;
    // private Node tail;
    private int size;

    public LinkedListDeque() {
        head = new Node();
        head.data = null;
        head.next = head;
        head.prev = head;
        // tail = head;
        size = 0;
    }

    private void add(T data, Node before) {
        Node t = before.next;
        Node node = new Node(data, before, t);
        before.next = node;
        t.prev = node;
        size++;
    }

    @Override
    public void addFirst(T data) {
        add(data, head);
        
    }

    @Override
    public T removeFirst() {
        if (head.next == head) {
            return null;
        }
        return remove(head);
    }

    @Override
    public T removeLast() {
        if (head.next == head) {
            return null;
        }
        // tail = tail.prev;
        return remove(head.prev.prev);
    }

    private T remove(Node before) {

        Node t = before.next;
        before.next = t.next;
        t.next.prev = before;
        T data = t.data;
        t = null;
        size--;
        return data;
    }

    @Override
    public void addLast(T data) {
        add(data, head.prev);
        // tail = tail.next;
    }

    @Override
    public boolean isEmpty() {
        return head.next == head;
    }

    @Override
    public void printDeque() {
        Node node = head.next;
        while (node != head) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        
        Node t = head.next;
        while (index > 0) {
            index -= 1;
            t = t.next;
        }
        return t.data;
    }

    private T getRecursive(Node node, int index) {
        if (index == 0) {
            return node.data;
        }
        return getRecursive(node.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursive(head.next, index);
    }
}
