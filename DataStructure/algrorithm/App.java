import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class App {

    static final Integer seed = 114514;
    static final Random RANDOM = new Random(seed);

    class Pair {
        int p, q;

        public Pair(int p, int q) {
            this.p = p;
            this.q = q;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + p;
            result = prime * result + q;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            if (p != other.p)
                return false;
            if (q != other.q)
                return false;
            return true;
        }

        private App getEnclosingInstance() {
            return App.this;
        }
        
        
    }



    @Test
    public void unionTest() {
        final int bound = 1000;
        Union union = new Union(bound);
        Set<Pair> set = new HashSet<>();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            int p = RANDOM.nextInt(bound);
            int q = RANDOM.nextInt(bound);
            int r = RANDOM.nextInt(2);
            switch (r) {
                case 0:
                    str.append("union.connect(" + p + "," + q + ")\n").
                            append("union.isConnect(" + p + "," + q + ")\n");
                    union.connect(p, q);
                    set.add(new Pair(p, q));
                    assertTrue(str.toString(), union.isConnect(p, q));
                    break;
                
            }
        }
    }



    public static void main(String[] args) {
        
        Random random = new Random(seed);
        Integer[] nums11 = {1};
        Integer[] nums22 = {2,3};
        Integer[] nums33 = {7,3,10,23,41,1};
        List<Integer> nums1 = Arrays.asList(1);
        List<Integer> nums2 = Arrays.asList(2,3);
        List<Integer> nums3 = Arrays.asList(7,3,10,23,41,1);
        List<Integer> orz = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            orz.add(random.nextInt(200));
        }
        // Integer[] ret = Sort.mergeSort(nums1, nums2, new Comparator<Integer>() {

        //     @Override
        //     public int compare(Integer o1, Integer o2) {
        //         return o1.compareTo(o2);
        //     } 
        // }, Integer.class);
        Comparator<Integer> cc = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {

                return o1.compareTo(o2);
            }
            
        };

        List<Integer> ret = Sort.mergeSort(orz, cc);
        for (int i = 1; i < ret.size(); i++) {
            // assert ret.get(i - 1) < ret.get(i);
            if (ret.get(i - 1) > ret.get(i)) {
                System.out.println("error at index" + i + " i - 1 is " + ret.get(i - 1) + " i is " + ret.get(i));
            }
        }
        // Sort.insertSort(nums3, cc);
        // for (Integer num : nums3) {
        //     System.out.println(num);
        // }

        // List<Integer> ret = Sort.merge(nums1, nums2, cc);
        // for (Integer num : ret) {
        //     System.out.println(num);
        // }
    }
}
