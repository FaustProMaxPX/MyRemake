使用语言：Java

> 实例化对象的过程

首先根据构造函数传入的参数初始化对象中的属性，然后该对象的属性的首个bit所在的位置返回，作为该对象的地址被存入到对应的变量中。

所有地址长度位64位，null的地址为全0。



tips：所有的赋值操作都是将该变量存储的字节全部拷贝到另一个变量中。



## 链表

尽量避免特判可有效提高代码健壮性。

表头添加哨兵结点的思想：让链表在任何情况下的状态都遵循同一套操作逻辑。
