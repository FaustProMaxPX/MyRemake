package list;

public class App {
    
    public static void main(String[] args) {
        
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if (i == 25)
                list.addLast(0);
            else
                list.addLast(i);
        }
        list.addFirst(-1);
        list.add(25, 26);
        
        for (int i = 0; i <= 50; i++)
            assert list.get(i) == i : "Position " + i + " expected get " + list.get(i);
    }
}
