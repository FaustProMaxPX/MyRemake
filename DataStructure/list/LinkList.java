package list;

/** The double linked list */
public class LinkList <T> {

    /** inner class of node in the list */
    class Node {

        /** AF(data, prev, next) : The node's value is data, 
         * it's following prev and followed by next 
         * 
         * Rep:
         * 
         * prev != null && next != null
         * */
        T data;
        Node prev;
        Node next;

        /**
         * create a Node with the value of data
         * it is circulated
         * @param data the value of node's data
         */
        Node(T data) {
            this.data = data;
            this.next = this;
            this.prev = this;
        }
    }

    private Node head;
    private int size;

    public LinkList() {
        head = new Node(null);
        size = 0;
    }

    public void addFirst(T data) {
        addNode(head, data);
    }

    public void addLast(T data) {
        // Node node = new Node(data);
        Node tail = head.prev;
        addNode(tail, data);
    }
    
    /**
     * add the node with value of {data} behind {node}
     * @param node The node wait to be linked
     * @param data The data which the new node takes with, not null
     */
    private void addNode(Node node, T data) {

        Node addNode = new Node(data);
        Node prevNext = node.next;
        node.next = addNode;
        addNode.prev = node;
        addNode.next = prevNext;
        prevNext.prev = addNode;
        size++;
    }

    public int size() {
        return size;
    }

    /**
     * add the node behind {index} node
     * @param data The new node's data, not null
     * @param index The location of the node needed to be relinked, index >= 0 && index <= size
     */
    public void addNodeAt(T data, int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        Node t = head;        
        while (index > 0) {
            t = t.next;
            index--;
        }
        addNode(t, data);
    }

    /**
     * get the data of the node at {index}
     * @param index the location of the node
     * @return the data of that node
     */
    public T indexAt(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node t = head;
        while (index >= 0) {
            index--;
            t = t.next;
        }
        return t.data;
    }

    public static void main(String[] args) {
        
        LinkList<Integer> list = new LinkList<>();
        list.addFirst(1);
        list.addLast(2);
        list.addNodeAt(10, 2);
        list.addNodeAt(20, 2);
        for (int i = 0; i < list.size(); i++)
            System.out.printf("%d" ,list.indexAt(i));
    }
}