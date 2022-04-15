public class HeapTest {
    
    public static void main(String[] args) {
        // Random random = new Random();
        // int max = -1;
        // int min = 100;
        // Heap heap = new Heap(10);
        // for (int i = 0; i < 8; i++) {
        //     int num = random.nextInt(100);
        //     min = Math.min(min, num);
        //     max = Math.max(max, num);
        //     heap.add(num);
        // }
        // int ret = heap.remove();
        // if (ret != min) {
        //     System.out.println("expect " + min + " but get " + ret);
        // }
        Integer[] nums = {1,10,8,7,52,3};
        Heap heap = new Heap(nums);
        int num = heap.remove();
        if (num != 1) {
            System.out.println("expect " + 1 + " but get " + num);
        }
    }
}
