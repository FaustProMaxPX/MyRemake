# C++

## Stream



> ostream

只能使用`<<`运算符

**将任何类型的数据转换成字符串并传输给流**

> istream

只能使用`>>`运算符

**将流中的字符串转换成对应的数据类型**



> cin

每一次读取数据只会读取到下一个空白符为止。所有没有被读走的数据会保留在缓冲区。



> 4种表示流状态的的bit



Good bit : 准备好读写

Fail bit : 之前的操作失败，之后的所有bit都被冻结

EOF bit :  之前的bit已经到达文件尾部

Bad bit : 外部错误，无法从外部数据源读取数据到缓冲区，通常无法恢复





## Container



> vector

若直接使用下标访问，会将数组超限错误静默处理。建议使用at()方法。



> deque

头插速度更加快，但是访问元素速度相对较慢。



> map/set

数据按照键排序存储（排序方式自定义），在迭代访问一系列元素时速度较快



> unordered_map/unordered_set

数据无序存储，访问单个元素时较快。



> multimap

一个键可以对应多个值

```C++
unordered_multimap<string,int> map;
    map.insert({"cxc", 1});
    map.insert({"cxc", 10});
```



> iterator

分类：

- 输入/输出迭代器
- Forward iterator : 同时具备写入与读取的功能
- Bidirectional iterator : 具有以上所有功能，且既可以向前也可以向后移动，接受`--`运算符
- Random Access iterator : 具有以上所有功能，且可对迭代器任意加减某个数。



## Class

struct 就是一个特殊的类，但是他的所有属性都是公开的。

#### 修饰词

> private

通常包含所有的类属性。并且用户不能直接访问或修改被`private` 修饰的内容。

> public

可以被用户直接访问。通常定义了一系列与私有内容实现交互的内容。



tips:C++中的this是指向当前对象的一个指针，不是引用。



#### 折构函数

删除单个对象关键字：delete

删除数组关键字：delete []

折构函数声明：ClassName::~ClassName()

折构函数不必显示调用，在离开作用域时会被自动调用。



### Lambada

语法：

[capture clause] (parameter) -> returnType {function body}

capture clause:give access to outside variables



stable_partition()

```C++
BidirectionalIterator stable_partition (BidirectionalIterator first,
                                        BidirectionalIterator last,
                                        UnaryPredicate pred);
```

将集合中的元素分成两组，一组符合pred要求，一组不符合，并且不改变各元素相对位置。



tips：如果调用非STL内置的方法对容器进行操作，该方法只能操作容器内容，但容器大小不会动态变化。

