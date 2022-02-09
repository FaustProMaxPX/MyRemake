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


