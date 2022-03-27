import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import jh61b.junit.TestRunner;


public class TestArrayDequeGold {
    

    @Test
    public void emptyTest() {

        StudentArrayDeque<Integer> deque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> answer = new ArrayDequeSolution<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            int bo = 0;
            if (deque.size() == 0) {
                bo = StdRandom.uniform(2);
            }
            else {
                bo = StdRandom.uniform(7);
            }
            Integer n = StdRandom.uniform(100);
            Integer d,a;
            switch (bo) {
                 case 0:
                    builder.append("deque.addFirst(" + n + ")\n");
                     deque.addFirst(n);
                     answer.addFirst(n);
                     break;
                case 1:
                    builder.append("deque.addLast(" + n + ")\n");
                    deque.addLast(n);
                    answer.addLast(n);
                    break;   
                case 2:
                    d = deque.removeLast();
                    a = answer.removeLast();
                    builder.append("deque.removeLast()\n" );
                    assertEquals(builder.toString(), a, d);
                    break;
                case 3:
                    d = deque.removeFirst();
                    a = answer.removeFirst();
                    builder.append("deque.removeFirst()\n");
                    assertEquals(builder.toString(), a, d);
                    break; 
                case 4:
                    builder.append("deque.size()\n");
                    assertEquals(builder.toString(), answer.size(), deque.size());
                    break;
                case 5:
                    builder.append("deque.isEmpty()\n");
                    boolean t = deque.isEmpty() ^ answer.isEmpty();
                    assertFalse(builder.toString(), t);
                    break;
                case 6:
                    // if (deque.size() < 0) {
                    //     assertTrue(builder.toString() + "deque size" ,false);
                    // }
                    // builder.append("deque.size()\n");
                    // System.out.println(deque.size() + " " + answer.size());
                    // assertEquals(builder.toString(), answer.size(), deque.size());
                    int index = StdRandom.uniform(deque.size());
                    builder.append("deque.get(" + index + ")\n");
                    assertEquals(builder.toString(), answer.get(index), deque.get(index));
                 default:
                     break;
             }
        } 

    }

    public static void main(String[] args) {
        TestRunner.runTests(TestArrayDequeGold.class);
    }
}
