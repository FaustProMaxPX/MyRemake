public class ArrayDequeTest {
    
    public static void addTest() {

        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0)
                deque.addFirst(i);
            else 
                deque.addLast(i);
        }
        deque.printDeque();
    }

    public static void removeTest() {

        ArrayDeque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0)
                deque.addFirst(i);
            else 
                deque.addLast(i);
        }

        for (int i = 0; i < 90; i++) {
            if (i % 3 == 0)
                deque.removeFirst();
            else
                deque.removeLast();
        }
        deque.printDeque();
    }

    public static void main(String[] args) {
        // addTest();
        removeTest();
    }
}
