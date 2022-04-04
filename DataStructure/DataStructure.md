使用语言：Java

> 实例化对象的过程

首先根据构造函数传入的参数初始化对象中的属性，然后该对象的属性的首个bit所在的位置返回，作为该对象的地址被存入到对应的变量中。

所有地址长度位64位，null的地址为全0。



tips：所有的赋值操作都是将该变量存储的字节全部拷贝到另一个变量中。



## 链表

尽量避免特判可有效提高代码健壮性。

表头添加哨兵结点的思想：让链表在任何情况下的状态都遵循同一套操作逻辑。



在单链表中添加头部哑节点的原因是，在某些情况下无法访问链表中结点的next属性，因此我们通过添加哑节点来使next属性在任何时刻都可以被访问。

而对于双向链表，又出现了某些结点的prev属性不能被访问或是说不应该被访问。例如，按照原先单链表的设计，我们将需要在前插时对是否是头部哑节点进行特判。

此时有两种解决方法：

1. 在尾部也添加一个哑节点。
2. 将链表设计成循环链表。



## 顺序表

> resize

当顺序表需要进行resize操作时，将数组大小进行指数级更改，而非简单的加减。因为加减无法根据当前数据量的大小动态进行调整，导致在数据量过大时频繁地调用resize操作拖慢速度且占用大量空间。

若顺序表中数组存储的不是基本数据类型，则不推荐懒惰删除，因为被删除对象的引用仍然保存着，不会被gc回收，占用内存。



## 继承

继承会从父类继承所有的成员变量，静态变量，所有的方法和内部类。

但被private修饰的不可直接访问。

```java
// 调用父类方法
super.xxx();
```



tips:构造器都不会被继承，但Java会默认在子类构造器中首先调用基类默认*无参*构造器。



> 继承会破坏封装

Java根据动态数据类型调用方法，若子类中覆写了一个方法，其中调用了从基类继承来的方法，但此方法调用了基类中被覆写的方法，则此时不会调用父类中被覆写的方法，而是子类的方法，最终陷入无限循环。

封装被破坏的直接体现：模组中的方法不再以整体的形式被调用



## 排序



> 合并排序 (+ 选择排序)

合并排序在处理有序数组时仅有O(N)的复杂度，因此可以选择将无序数组切分成若干部分，对其分别调用选择排序，再进行合并，实现排序加速。



但若将数组切分到只剩单个元素的小块，则可直接用合并排序实现一切，复杂度为O(NlogN)，代价是内存占用较大。

```java
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
```



## 并查集

不实质性将两个结点连接，而是将其存储在同一个集合中，表明他们之间有链接。

这样在查询两个结点之间是否连接时只需要看他们是否在同一集合中。

re:我们不需要像图那样直到具体的连接情况，因此可以用逻辑上的连接代替。



> implement

实现方式1：哈希表（通用）



对于整形

实现方式2：创建一个大小为N的数组，数组中存储的是其对应下标数组所属的集合的代表数字。

快速连接的方法：直接修改集合的代表元素指向的父级结点的值。但此时父级结点的计算仍可能是一个昂贵的操作。最坏情况会退化到O(N)。

因此我们选择将一个结合中所有的元素都指向结合代表元素，这样查询当前集合的父节点的时间就被大大压缩。

此时需要考虑在合并时应将小的集合并到大的集合来减少树的深度。

-> 使用一个数组来追踪集合大小，确保每一次都是小的往大的合，这样树的深度永远被控制在logN。

路径压缩：在执行isConnect时，将单个集合中的所有结点指向其最高父结点。

