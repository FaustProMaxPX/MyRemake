# Make

## 1. 程序的编译与链接

编译： 将源文件编译成中间代码文件(.o文件)

​					要求语法正确，函数与变量声明正确

链接： 将大量的Object File 合成可执行文件

​					头文件所在位置，当中间文件很多时，需要给中间文件打包(生成 Archive File 即.a文件) 

## 2.  Makefile概述

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



## 3. 书写规则



### 3.1 文件查找

Makefile默认在当前目录下寻找目标文件和依赖文件

> VPATH 特殊变量

当存在`VPath`变量时，若在当前目录找不到文件，会到VPath下的路径寻找。

```makefile
VPATH = src:../headers
# 不同目录用 ':' 分割
```



> vpath 关键字

使用方法：

- vpath <pattern> <directories>
  为符合模式<pattern>的文件指定搜索目录<directories>。
- vpath <pattern>
  清除符合模式<pattern>的文件的搜索目录。
- vpath
  清除所有已被设置好了的文件搜索目录。

vapth使用方法中的<pattern>需要包含 `%` 字符。 `%` 的意思是匹配零或若干字符，（需引用 `%` ，使用 `\` ）例如， `%.h` 表示所有以 `.h` 结尾的文件。<pattern>指定了要搜索的文件集，而<directories>则指定了< pattern>的文件集的搜索的目录。例如：

```
vpath %.h ../headers
```



### 3.2 伪目标

伪目标是一个标签而不是文件，所以make无法生存它的依赖关系和决定是否要执行

> 显示指明为目标

```makefile
.PHONY : clean
clean : 
	command
```



### 3.3 多目标

若多个目标同时依赖于某个文件，则可将其合并

```makefile
bigoutput littleoutput : text.g
	generate text.g -$(subst output,,$@) > $@
```

上述规则等价于

```makefile
bigoutput : text.g
    generate text.g -big > bigoutput
littleoutput : text.g
    generate text.g -little > littleoutput
```



### 3.4 静态模式

静态模式可以更加容易地定义多目标的规则

语法：

```makefile
<targets ...> : <target-pattern> : <prereq-patterns ...>
    <commands>
    ...
```

targets : 定义了一系列的目标文件，可以有通配符，是一个集合

target-pattern : 指明了targets的模式，也就是目标集的模式

prereq-patterns : 目标的依赖模式，它对target-pattern形成的模式再进行一次依赖目标的定义。

如果我们的<target-pattern>定义成 `%.o` ，意思是我们的<target>;集合中都是以 `.o` 结尾的，而如果我们的<prereq-patterns>定义成 `%.c` ，意思是对<target-pattern>所形成的目标集进行二次定义，其计算方法是，取<target-pattern>模式中的 `%` （也就是去掉了 `.o` 这个结尾），并为其加上 `.c` 这个结尾，形成的新集合。



eg : 

```makefile
objects = foo.o bar.o
all : $(objects)
$(objects): %.o: %.c
	$(CC) -c $(CFLAGS) $< -o $@

# 展开后
#foo.o : foo.c
#    $(CC) -c $(CFLAGS) foo.c -o foo.o
#bar.o : bar.c
#    $(CC) -c $(CFLAGS) bar.c -o bar.o
```

指明了我们的目标从$object中获取， `%.o` 表明要所有以 `.o` 结尾的目标，也就是 `foo.o bar.o` ，也就是变量 `$object` 集合的模式，而依赖模式 `%.c` 则取模式 `%.o` 的 `%` ，也就是 `foo bar` ，并为其加下 `.c` 的后缀，于是，我们的依赖目标就是 `foo.c bar.c` 。而命令中的 `$<` 和 `$@` 则是自动化变量， `$<` 表示第一个依赖文件， `$@` 表示目标集



### 3.5自动生成依赖型

```shell
# 查看指定文件的依赖性
gcc -M main.c
gcc -MM main.c
```



为每一个.c文件生成一个.d文件，.d文件存放.c文件的依赖性

生成.d文件的规则

```makefile
%.d: %.c
    @set -e; rm -f $@; \
    $(CC) -MM $(CPPFLAGS) $< > $@.$$$$; \
    sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
    rm -f $@.$$$$
```

这个规则的意思是，所有的 `.d` 文件依赖于 `.c` 文件， `rm -f $@` 的意思是删除所有的目标，也就是 `.d` 文件，第二行的意思是，为每个依赖文件 `$<` ，也就是 `.c` 文件生成依赖文件， `$@` 表示模式 `%.d` 文件，如果有一个C文件是name.c，那么 `%` 就是 `name` ， `$$$$` 意为一个随机编号，第二行生成的文件有可能是“name.d.12345”，第三行使用sed命令做了一个替换，关于sed命令的用法请参看相关的使用文档。第四行就是删除临时文件。

目的：

将

```makefile
main.o : main.c defs.h
```

转成：

```makefile
main.o main.d : main.c defs.h
```

使得.d文件可以自动更新



## 4. 书写命令



### 4.1显示命令

若在命令行前加`@`，则该命令不会被显示出来。

```makefile
@echo 'Compiling'
```



make -n 只显示命令，但不会执行，方便查看命令执行顺序

make -s 全面禁止命令的显示



### 4.2命令执行

如果需要上一条命令的结果被应用于下一条命令，则应用`;`间隔两条命令，而不是换行

```makefile
exec:
	cd ~; pwd
```



make -i 忽略所有错误

make -k 若某命令出错，则终止该命令，但其他命令照常执行



### 4.3 嵌套执行make

> 主控make

```makefile
subsystem:
    cd subdir && $(MAKE)
```

> 传递变量

```makefile
export variable #传递变量
export #传递所有变量
unexport variable #不传递
```

有两个变量，一个是 `SHELL` ，一个是 `MAKEFLAGS` ，这两个变量不管你是否export，其总是要传递到下层 Makefile中，特别是 `MAKEFLAGS` 变量，其中包含了make的参数信息，如果我们执行“总控Makefile”时有make参数或是在上层 Makefile中定义了这个变量，那么 `MAKEFLAGS` 变量将会是这些参数，并会传递到下层Makefile中，这是一个系统级的环境变量。



但是make命令中的有几个参数并不往下传递，它们是 `-C` , `-f` , `-h`, `-o` 和 `-W`



### 4.4定义命令包

如果Makefile中出现一些相同命令序列，那么我们可以为这些相同的命令序列定义一个变量。定义这种命令序列的语法以 `define` 开始，以 `endef` 结束

```makefile
define run-yacc
yacc $(firstword $^)
mv y.tab.c $@
endef
```



## 5. 变量



### 5.1 变量的基础

变量在声明时要赋初值，在使用时，在变量名前加`$`，并将变量名用()包起来。

若要使用真实的`$`，输入`$$`



> 变量的定义

使用`:=`运算符来定义变量

```makefile
x := foo
y := $(x) bar
x := later
```

如此，前面的变量便不能使用后面变量的值。

如果直接用`=`赋值，则前面的变量就不可以使用后面的变量（不推荐）



`#注释符可以用来表示变量定义的终止`

```makefile
nullstring :=
space := $(nullstring) #end of line
dir := /foo/bar    # 4 space left
```



`?=`在变量未被赋值的情况下给他赋值，否则略过这条语句

```makefile
foo ?= bar
```



### 5.2 变量高级用法



> 变量值的替换

`$(var:a=b)`将变量var中所有以字串a**结尾**的‘a’替换为字串'b'。此处结尾指的是空格或结束符

`$(var:%.c=%.d)`作用同上，此种通过静态模式定义。



> 使用变量的变量

Makefile中的变量类似于宏，可以用于拼接任意东西

```makefile
x = variable1
variable2 := Hello
y = $(subst 1,2,$(x))
z = y
a := $($($(z)))
```



```makefile
first_second = Hello
a = first
b = second
all = $($a_$b)
# $a_$b 拼接成变量名
```



```makefile
ifdef do_sort
    func := sort
else
    func := strip
endif

bar := a d b g q c

foo := $($(func) $(bar))
# $(func) $(bar) 分别拼接成函数名及其参数
```



> 追加变量值

`+=`

```makefile
variable := value
variable += more
#=== 等价于 ===#
variable := value
variable := $(variable) more
```



> 多行变量

利用`define`关键字设置变量的值可以有换行，便于定义一系列命令。

变量的值可以包含函数、命令、文字，或是其它变量。因为命令需要以[Tab]键开头，所以如果你用define定义的命令变量中没有以 `Tab` 键开头，那么make 就不会把其认为是命令。

```makefile
define two-lines
echo foo
echo $(bar)
endef
```



> override

如果有变量是通常make的命令行参数设置的，那么Makefile中对这个变量的赋值会被忽略。如果你想在Makefile中设置这类参数的值，那么，你可以使用“override”指示符。其语法是:

```makefile
override <variable>; = <value>;

override <variable>; := <value>;
```

当然，你还可以追加:

```
override <variable>; += <more text>;
```



### 5.3 特殊变量



> 环境变量

make运行时的系统环境变量可以在make开始运行时被载入到Makefile文件中，但是如果Makefile中已定义了这个变量，或是这个变量由make命令行带入，那么系统的环境变量的值将被覆盖。（如果make指定了“-e”参数，那么，系统环境变量将覆盖Makefile中定义的变量）

因此，如果我们在环境变量中设置了 `CFLAGS` 环境变量，那么我们就可以在所有的Makefile中使用这个变量了。这对于我们使用统一的编译参数有比较大的好处。如果Makefile中定义了CFLAGS，那么则会使用Makefile中的这个变量，如果没有定义则使用系统环境变量的值，一个共性和个性的统一，很像“全局变量”和“局部变量”的特性。

当make嵌套调用时（参见前面的“嵌套调用”章节），上层Makefile中定义的变量会以系统环境变量的方式传递到下层的Makefile 中。当然，默认情况下，只有通过命令行设置的变量会被传递。而定义在文件中的变量，如果要向下层Makefile传递，则需要使用exprot关键字来声明。（参见前面章节）



> 目标变量

为某个目标设置局部变量，其作用范围只在这条规则及其连带的规则中。

语法：

```makefile
<target ...> : <variable-assignment>
# 主要用于处理环境变量
<target ...> : override <variable-assignment> 
```



```makefile
prog : CFLAGS = -g
prog : prog.o foo.o bar.o
    $(CC) $(CFLAGS) prog.o foo.o bar.o

prog.o : prog.c
    $(CC) $(CFLAGS) prog.c

foo.o : foo.c
    $(CC) $(CFLAGS) foo.c

bar.o : bar.c
    $(CC) $(CFLAGS) bar.c
```



> 模式变量

将变量定义在所有符合模式的目标上

```makefile
%.o : CFLAGS = -o
```

语法同目标变量
