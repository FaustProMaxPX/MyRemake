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

```java
public class Union {
    
    Integer[] parent;
    Integer[] size;

    /*
        AF(n) : a union containing n elements, 
        their parent nodes are stored in {parent}
        every set's size is stored in size

        Rep invarient:
        parent != null, elements in parent are greater than or equal to 0, less than n
        size != null, elements in size are not less than 1
    */

    public Union(int n) {
        parent = new Integer[n];
        size = new Integer[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * check if p and q are belong to the same set
     * compress the path at the same time
     * @param p node p
     * @param q node q
     * @return if they are belong to the same set
     */
    public boolean isConnect(int p, int q) {
        int i = find(p);
        int j = find(q);
        return i == j;
    }

    /**
     * find parent of p
     * @param p node p
     * @return its parent
     */
    public int find(int p) {
        if (p == parent[p]) {
            return p;
        }
        return parent[p] = find(parent[p]);
    }

    /**
     * connect set containing p and set containing q together
     * @param p node p
     * @param q node q
     */
    public void connect(int p, int q) {

        int i = parent[p];
        int j = parent[q];
        if (i == j)
            return;
        if (size[i] >= size[j]) {
            parent[j] = i;
            size[i] += size[j];
        }
        else {
            parent[i] = j;
            size[j] += size[i];
        }
    }
}
```



## 树

> 前/中/后序遍历时间复杂度

因为该遍历算法针对每一个结点作处理，其时间复杂度增长与结点个数有关。一次遍历要考虑N个节点，因此时间复杂度为O(N)

> 层序遍历

层序遍历的区别在于，该算法时间复杂度的增长与层数有关，假设层数为H，操作次数为1 + 2^1 + 2^2 + ...

因此其时间复杂度为O(2^H)，而H=logN，所以时间复杂度为O(N)



## 二叉搜索树

对于一张有序的顺序表，可以通过不断的将其二分来进行压缩，BST就是二分查找的显式表现。

根节点的左子树这种的元素均小于根节点的元素，右子树的均大于根节点元素。



> 删除操作

对于叶子节点，直接将其删除即可。

对于有单个孩子的结点，用其孩子结点顶替当前结点。

对于有两个孩子的结点，找到其左子树中最大的结点或右子树中最小的结点(必然是叶子结点或只有一个孩子的结点)来顶替它，然后删除用于顶替的结点。

tip：但是删除操作做的越多，树的高度就会不断增高，因为每次删除都相当于让树的某一端的结点数量减少，最后失去平衡。



```java

public class BinSearchTree {

    private Node root;
    private int size;
    
    public Integer get(Integer key) {
        return getHelper(key, root);
    }

    private Integer getHelper(Integer key, Node troot) {

        if (troot == null) {
            return null;
        }
        else if (troot.elem.key == key) {
            return troot.elem.value;
        }
        else if (troot.elem.key < key) {
            return getHelper(key, troot.right);
        }
        return getHelper(key, troot.left);
        
    }

    public Node insert(Integer key, Integer value) {
        root = insertHelper(key, value, root);
        return root;
    }

    private Node insertHelper(Integer key, Integer value, Node troot) {
        if (troot == null) {
            size++;
            return new Node(new Pair(key, value), null, null);
        }
        else {
            if (troot.elem.key > key) {
                troot.left = insertHelper(key, value, troot.left);
            }
            else if (troot.elem.key < key) {
                troot.right = insertHelper(key, value, troot.right);
            }
            return troot;
        }
    }

    public boolean containsKey(Integer key) {
        return getHelper(key, root) != null;
    }

    public void delete(Integer key) {
        deleteHelper(key, root);
    }

    private Node deleteHelper(Integer key, Node troot) {
        
        if (troot == null) {
            return null;
        }
        else if (troot.elem.key < key) {
            troot.right = deleteHelper(key, troot.right);
        }
        else if (troot.elem.key > key) {
            troot.left = deleteHelper(key, troot.left);
        }
        else {
            if (troot.left != null && troot.right != null) {
                Node tmp = findMax(troot.left);
                troot.elem = tmp.elem;
                troot.left = deleteHelper(troot.elem.key, troot.left);
            }
            else {
                if (troot.left == null) {
                    troot = troot.right;
                }
                else if (troot.right == null) {
                    troot = troot.left;
                }
            }
            size--;
        }
        return troot;
    }

    private Node findMax(Node troot) {
        if (troot == null) {
            return null;
        }
        while (troot.right != null) {
            troot = troot.right;
        }
        return troot;
    }

    private class Node {
        private Pair elem;
        private Node left;
        private Node right;
        public Node(Pair elem, BinSearchTree.Node left, BinSearchTree.Node right) {
            this.elem = elem;
            this.left = left;
            this.right = right;
        }       
    }

    private class Pair {

        private Integer key;
        private Integer value;
        public Pair(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
        
    }
}
```



## 平衡树

在已知一组数据的情况下，若将其按随机顺序插入，则复杂度大概率是O(logN)，但若不提前知道数据，那么有可能会让树退化成单链表，因为先前并没有考虑该往哪里插入数据，基本都是插在底部，因此很有可能只往一端插入数据，让树的高度大幅增加。



因此我们需要在每一次插入与删除之后检查树的平衡，若失衡则进行调整。



> 左旋

右端大量的数据破坏了树的平衡，则将原来的根节点作为新根节点的左子树，新根节点原来的左子树接到原根结点的右端。右旋同理。



> B tree

使一个结点能够存储多个数据，例如每个结点允许存储3个数据，当数据量大于3时，将当前结点拆分，将第2大的数据放到父节点中存储。这样之后每个结点都有4棵子树，分别存储 < val1, >val1 && < val2，...的数据。只有当根节点中的数据也超过上限时，树的高度才会增加，将第2大的数据作为新的根节点，然后重新分配子树。

![image-20220406171720216](C:\Users\ABD\AppData\Roaming\Typora\typora-user-images\image-20220406171720216.png)



> 红黑树

红黑树即是B树(2-3树)和二叉树的结合，对于含有两个数据的结点，**逻辑上**添加一个哑结点作为这两个数据的父节点，这样就将他们拆分成二叉树，这个哑结点即为红黑树中的红。



## Hash

将数据本身当作索引，仅仅把数据看作是位，而在数组对应的位置存储布尔值。

但该方法会占用大量空间，且对于非数字类型不友好（但可以进行映射，确保任何值都会被分配到唯一的一个空间）。

将元素转换为下标的过程即为hash，注意不要让hash生成负数，或对其进行特殊处理。

对于超过2亿的数据量，必然会有多个元素共享空间的情况，因此我们不在数组中存储true/false,而是对应的元素

哈希的速度取决于它存储的数据量（越多的数据量代表越多的冲突），N个数据分布在M个桶里时，其平均访问速度为O(N/M), L=N/M即为装在因子。当装载因子超过阈值时，就要进行rehash操作，通过使用更多的桶来减少冲突。

但装载因子不是唯一需要考虑的东西，当一系列数据共享同一个空间时，其装载因子也可能非常小，因此哈希函数的涉及应确保哈希表中的数据分散存放。





## Heap

可以追踪或移除最大/最小元素

堆用树的形式体现具有如下特质：

1. 所有的结点都要小于等于它的子节点
2. 完全



> add

先将要插入的结点放在最后一个位置，若其父节点的值大于其值，则将父节点沉降，直到父节点的值小于该节点的值，此时空槽到达满足要求的位置。



> remove

先将最后一个结点提升为root结点，然后沉降直到它的值小于它的孩子节点。



> 用数组表示树

1. 将键存储在一个数组中，将对应的父节点id存在另一个数组中

2. 只存储数据，不存储结构，假设是一个完整二叉树。

   父节点与子节点的下标关系：

   从1开始存储

   - parent = k/2
   - left child = 2 * k
   - right child = 2 * 



## Graph

> 临接矩阵

用二维数组存储图，其矩阵的行列分别表示起点与终点，矩阵中存储的内容表示两点是否连接，或者是两点间边的权重。

遍历的时间复杂度为O(V^2)

首先要将所有点当作起点展开一层循环，该层循环会持续执行V次，接着要检查当前起点所连接到的点，要遍历数组matrix\[v][]，该层循环每轮要执行V次，由此可得时间复杂度为V^2次



> 临接表

维护一组链表，用数组下标表示起点，链表内容表示它连通的结点

遍历的时间复杂度为O(V + E)

首先仍要将所有结点都搜索一遍，但针对相邻点的处理有所不同，临接矩阵需要访问所有的点来确定是否连接，而临接矩阵只要访问对应下标上的链表长度的数据。对于没有相邻点的结点，其访问连接点只需要进行1次操作，即在图中没有边时，临接表只需进行V次操作，而当临接矩阵中出现边时，就需要再加上E次操作，因此总时间复杂度为O(V + E)



> 深度优先搜索

遍历完某一结点的所有子节点后再转向另一结点。需要一个数组来保存访问情况，避免重复访问导致无限递归。



> 图论算法的设计模式

将图的信息传递给专门进行处理的客户类，让客户直接从对应的类中获取需要的处理后的信息。

![image-20220415191751741](C:\Users\Faust\AppData\Roaming\Typora\typora-user-images\image-20220415191751741.png)



> Topological Sort

拓扑排序是一个**有向无环图**的所有顶点的线性序列，且具有如下特质：

1. 每一个顶点出现且只出现一次
2. 若存在一条从A到B的路径，则在序列中A出现在B之前。

实现：

1. 维护一个存储入度为0的节点的数组
2. 每次从该数组中取出一个节点并删除该顶点及所有相关的有向边。
3. 更新图中其余节点的入度。
4. 重复2，3直到图为空。

应用：拓扑排序常用来排序具有依赖关系的任务。



> Shortest Path

广度优先搜索不适合做最短路检索的原因：没有考虑边的权值。

最短路径最后的生成结果是一棵树

树的特性：

1. 没有环路(最短路径问题本身需要满足的条件)
2. 每一个节点都最多只有一个父节点(对于图中的每一个节点来说，到达它的最短路径数永远<=1)

![image-20220417101232592](C:\Users\Faust\AppData\Roaming\Typora\typora-user-images\image-20220417101232592.png)



Dijkstra算法

按照当前已知最短路的顺序访问各个节点，然后对该节点拥有的边进行松弛操作。



A*算法

基本和Dijkstra算法一致，但他访问节点的顺序是按照已知最短路再加上到达下一个节点的估计。和Dijkstra算法相比，该算法的速度会更快，因为D算法的执行顺序是根据到起点的距离，而A*算法加入了对终点距离的估计。

