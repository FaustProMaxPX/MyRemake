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
            Integer ret;
            if (i % 3 == 0)
                ret = deque.removeFirst();
            else
                ret = deque.removeLast();
            System.out.print(ret + " ");
        }
        for (int i = 0; i < 12; i++) {
            deque.removeLast();
        }
        System.out.println(deque.size());
        deque.printDeque();
    }

    public static void getTest() {

        ArrayDeque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0)
                deque.addFirst(i + "");
            else 
                deque.addLast(i + "");
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(deque.get(i));
        }
    }

    public static void emptyTest() {

        ArrayDeque<Boolean> deque = new ArrayDeque<>();
        System.out.println(deque.isEmpty());
        for (int i = 0; i < 100; i++) {

            if (i % 3 == 0) {
                deque.addFirst(i % 2 == 0 ? true : false);
            }
            else
                deque.addLast(i % 2 == 0 ? true : false);
        }
        System.out.println(deque.isEmpty());
        for (int i = 0; i < 100; i++)
            deque.removeFirst();
        System.out.println(deque.isEmpty());
    }

    public static void main(String[] args) {
        // addTest();
        // removeTest();
        // getTest();
        emptyTest();
    }
}
