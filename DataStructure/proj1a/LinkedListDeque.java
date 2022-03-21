
public class LinkedListDeque <T> {
    
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
    private Node tail;
    private int size;

    public LinkedListDeque() {
        head = new Node();
        head.data = null;
        head.next = head;
        head.prev = head;
        tail = head;
        size = 0;
    }

    private void add(T data, Node before) {
        Node t = before.next;
        Node node = new Node(data, before, t);
        before.next = node;
        t.prev = node;
        size++;
    }

    public void addFirst(T data) {
        add(data, head);
        while (tail.next != head) {
            tail = tail.next;
        }
    }

    public T removeFirst() {
        if (head.next == head) {
            throw new RuntimeException("The list has no member");
        }
        return remove(head);
    }

    public T removeLast() {
        if (head.next == head) {
            throw new RuntimeException("The list has no member");
        }
        tail = tail.prev;
        return remove(tail);
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

    public void addLast(T data) {
        add(data, tail);
        tail = tail.next;
    }

    public boolean isEmpty() {
        return head.next == head;
    }

    public void printDeque() {
        Node node = head.next;
        while (node != head) {
            System.out.print(node.data + " ");
            node = node.next;
        }
    }

    public int size() {
        return size;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        Node t = head.next;
        while (index > 0) {
            index -= 1;
            t = t.next;
        }
        return t.data;
    }
}
