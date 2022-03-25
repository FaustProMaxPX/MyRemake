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



#### 构造函数

1. default construction: the construction takes no parameters
2. copy construction: use an object to construct another construction as the copy

```c++
vector<int> vec{} // default construction
vector<int> vec{1,2,3} // regular construction
```



做深拷贝时要注意，若拷贝对象中存储的是指针类型，则应深拷贝指针指向的对象而非指针本身。直接调用STL库中的拷贝构造只会深拷贝存储的对象。



copy assignment: *删除旧的对象*，将其变量名用于另一个变量的拷贝。首先确保`另一个变量`不是自己本身。

tips: 

- 拷贝构造器，拷贝赋值，析构函数要么全用默认，要么全部自定义。

- 应显示指明子类的构造函数，默认会使用基类的构造函数。

  ```c++
  class Drink {
      private:
      	std::string _flavor
      
  	public:
  		Drink() = default; // 显式指明默认构造器
      	Drink(std::string flavor) : _flavor(flavor) {}
  }
  
  class Tea : public Drink {
      public:
      	Tea() = default;
  	    Tea(string flavor) : Drink(flavor) {} // invoke base constructor
  }
  ```

  

若构造器使用explicit修饰，则该构造器不能对参数作隐式的类型转换，也不能用拷贝初始化。

```c++
explicit HashMap(size_t bucket_count, const H& hash = H());
```

对于声明了noexcept的函数，编译器会对其进行优化，但此类函数一旦抛出异常，就会终止程序。

```c++
void func() noexcept
```



Initializer_list constructor

```c++
myvector(const myvector<T>& other) : t_size(other.t_size), length(other.length) {
        datas = new T[length];
        for (int i = 0; i < t_size; i++) {
            datas[i] = other.datas[i];
        }
    }
```



#### 折构函数

删除单个对象关键字：delete

删除数组关键字：delete []

折构函数声明：ClassName::~ClassName()

折构函数不必显示调用，在离开作用域时会被自动调用。

**若一个类要被继承，则必须将其析构函数设置为虚函数**



#### 友元函数

允许类外的对象访问类内的私有对象



初始化列表

```c++
private:
	int logicalSize;
StringVector::StringVector() : logicalSize(0) {}
```



### 继承

语法

```C++
class A {
    public:
    	virtual void make() = 0; // The class implementing A must define this method
};

// 此处的public代表B中所有由A继承来的方法与属性的访问限制照旧
class B : public A { // B implement A
    ...
}
```



对于一个接口，其只能包含纯虚函数。

对于非虚成员，所有的在父类中与子类成员同名的成员都会被隐藏。

只有声明为虚函数的方法会被动态绑定到子类上，其余方法一概到编译类型中去寻找。



> concept (only C++20)

concept是一个在编译期间进行判定的断言，属于接口的一类

对模板类的类型参数进行一定的约束。

```c++
template <typename It, typename Type>
requires Input_Iterator<It> && Iterator_of<It> 
	&& Equality_comparable<Value_type<It>, Type>
int count(It it, Type val){
    ...
}

template <typename It, typename Type>
concept DerivedFrom = Input_Iterator<It> && Iterator_of<It> 
	&& Equality_comparable<Value_type<It>, Type>

template <class D, class B>
int count(It it, Type val) requires DerivedFrom<D, B>
{
    
}
```



## Lambada

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



## Const

 被const修饰的变量不可被修改，包括该变量中的内容。同时若一个函数的参数为const，则在该函数调用的所有函数中都必须满足const，(即不能将一个const参数传递给非const参数)。



>  const on objects

只允许调用const function 并且将所有的public成员当作被const修饰



> const on functions

保证这个函数不会调用除const functions之外的函数，并且不允许修改任何非静态，非可变的成员

```c++
void fun() const;
```



const pointer

```c++
// read from right to left
// const iterator 类似于常量指针
int * const p; // constant pointer to a non-const int

const int * p; // non-const pointer to a const int
// const_iterator 类似于指针常量
```



tips：一个被const修饰的对象同样不能调用非const方法，因此建议在实现一个类的时候，同时实现const和非const两个版本。



## lvalue and rvalue

lvalue:被变量名(地址)标识的表达式，可以用`&`取到地址

rvalue:临时变量，无法用&取到地址， 无法被保存。

右值只出现在寄存器中，而在堆栈中没有空间。



`&`为左值引用，只能绑定左值

`&&`为右值引用，只能绑定右值

tips:

- 被const修饰的左值引用可以绑定右值。 re：非const修饰的左值引用可以修改指向对象的内容，但右值明显是不可以被修改的。
- 右值引用绑定右值，但右值引用本身是个左值。





## RAII

使用new申请的存储空间位于堆中，若在指向它的指针消失前，其存储空间没有被释放，则其永远在堆中占有那部分空间，但无法被访问到，造成内存泄漏。



在程序中需要被手动释放的资源

|             | Acquire   | Release |
| ----------- | --------- | ------- |
| Heap memory | new       | delete  |
| Files       | open      | close   |
| Locks       | try_clock | unlock  |
| Sockets     | socket    | close   |



tips: 我们无法保证当异常抛出时，delete会被正常执行。因此需要妥善处理异常，避免内存泄漏。



> RAII

在构造器中获取所有资源

在析构函数中释放所有资源

```c++
// you don't need to invoke close
ifstream input("xxx.txt");

// you must invoke close
ifstream input();
input.open("xxx.txt");
input.close();
```



## SmartPointer

> std::unique_pointer

唯一指向某一块内存资源，并在指针超出作用域时删除对象。

因此该指针不允许拷贝。



> std::shared_pointer

内存资源可以被多个指针指向，在最后一个指向的指针消失时，摧毁对象。



相关构造器 (用这个)

make_XXX<class>(args)



## 多线程

当要进行多线程时，使用原子数据类型

相关库

![image-20220322230548330](C:\Users\ABD\AppData\Roaming\Typora\typora-user-images\image-20220322230548330.png)

