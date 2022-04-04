import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sort {

    /**
     * merge two sorted array to a sorted array
     * @param <T> type of data
     * @param datas1 a sorted array and all of its elements are not null
     * @param datas2 a sorted array and all of its elements are not null
     * @param c The comparator of type T
     * @return a sorted array containing all elements in datas1 and datas2
     */
    public static <T> List<T> merge(List<T> datas1, List<T> datas2, Comparator<T> cc) {
        int i, j;
        i = j = 0;
        // ret = (T[])(new Object[datas1.length + datas2.length]);
        List<T> ret = new ArrayList<>();
        int idx = 0;
        while (i < datas1.size() && j < datas2.size()) {
            int cmp = cc.compare(datas1.get(i), datas2.get(j));
            if (cmp <= 0) {
                ret.add(datas1.get(i));
                ++i;
            }
            else {
                ret.add(datas2.get(j));
                ++j;
            }
            idx++;
        }
        if (i != datas1.size()) {
            ret.addAll(datas1.subList(i, datas1.size()));
        }
        else if (j != datas2.size()) {
            ret.addAll(datas2.subList(j, datas2.size()));
        }
        return ret;
    }

    public static <T> void insertSort(List<T> datas, Comparator<T> cc) {

        for (int i = 1; i < datas.size(); i++) {
            T elem = datas.get(i);
            int j;
            for (j = i; j > 0 && cc.compare(datas.get(j - 1), elem) > 0; j--) {
                datas.set(j, datas.get(j - 1));
            }
            datas.set(j, elem);
        }
    }

    public static <T> List<T> mergeSort(List<T> datas, Comparator<T> cc) {
        if (datas.size() < 10) {
            insertSort(datas, cc);
            return datas;
        }
        else {
            int mid = datas.size() / 2;
            List<T> sort1 = mergeSort(datas.subList(0, mid), cc);
            List<T> sort2 = mergeSort(datas.subList(mid, datas.size()), cc);
            List<T> ret = merge(sort1, sort2, cc);
            return ret;
        }
    }
}