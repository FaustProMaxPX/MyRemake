# Make

## 1.程序的编译与链接

编译： 将源文件编译成中间代码文件(.o文件)

​					要求语法正确，函数与变量声明正确

链接： 将大量的Object File 合成可执行文件

​					头文件所在位置，当中间文件很多时，需要给中间文件打包(生成 Archive File 即.a文件) 

## 2. Makefile概述

### 2.1 Makefile规则

```makefile
target ... : prerequisites ...
	command
	...
```

- target : 要生成的目标文件/可执行文件/标签
- prerequisites : 生成 target 需要的依赖文件
- command : 该 target 要执行的Shell命令 (以tab开始)



eg:

```bash
tes : test.o
	cc -o tes test.o
test.o : test.c def.h
	cc -c test.c
.phony : clean
clean :
	 rm test.o tes
```



### 2.2 Makefile变量

> 变量定义

```
objects = main.o kbd.o command.o display.o \
     insert.o search.o files.o utils.o
     
可理解为一个字符串
```

> 变量使用

$(objects)



### 2.3 Makefile自动推导

可以自动推导文件以及文件依赖关系后面的命令

只要make看到一个 `.o` 文件，它就会自动的把 `.c` 文件加在依赖关系中，如果make找到一个 `whatever.o` ，那么 `whatever.c` 就会是 `whatever.o` 的依赖文件。并且 `cc -c whatever.c` 也会被推导出来



### 2.4 Makefile 文件名

make 会依次在当前目录下寻找 GNUmakefile , makefile , Makefile(建议使用)

指定make文件 : `make -f filename`



### 2.5 引用其他Makefile

 

```
include filename
```

make命令开始时，会找寻 `include` 所指出的其它Makefile，并把其内容安置在当前的位置。就好像C/C++的 `#include` 指令一样。如果文件都没有指定绝对路径或是相对路径的话，make会在当前目录下首先寻找，如果当前目录下没有找到，那么，make还会在下面的几个目录下找：

1. 如果make执行时，有 `-I` 或 `--include-dir` 参数，那么make就会在这个参数所指定的目录下去寻找。
2. 如果目录 `<prefix>/include` （一般是： `/usr/local/bin` 或 `/usr/include` ）存在的话，make也会去找。