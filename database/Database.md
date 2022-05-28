# Database



## SQL



### Grammar

sql描述的是我需要什么，而非怎么做。底层交给DBMS实现。

> limit

limit [Integer]

查找表中前Integer条记录。通常与orderby一同使用。



> group by

将表切分成多个组，并以表为单位进行相关操作。

group by不允许直接查询非group by参数的字段。

re: group by会将许多记录作为整体操作，但非group by参数字段在分类后其数量在同一组中也势必>=1 无法成为一个整体被反映到表中，因此不被允许。

所有的聚合函数都需要针对一个组来使用，如果不显式指明，则整张表作为一个组。



Having对查询出的组根据给定条件进行过滤。



> like

```sql
WHERE xxx LIKE 'B_%'
-- _ means any character. % means any number of that character

-- Regular expression
-- ~ means match
WHERE xxx ~ 'B.*'

```



> 集合运算

UNION: 取并集

INTERSECT: 取交集

EXCEPT: 取差集

加上ALL代表显示重复值。



> view

```sql
create view view_name
as select_statement
```

临时视图

```sql
SELECT bname, scount
FROM Boats2 B,
(
	SELECT B.bid, COUNT(*)
    FROM R.bid = B.bid AND B.color = 'red'
    GROUP BY b.bid
) AS Reds(bid, scount)
WHERE Reds.bid = B.bid AND scount < 10

WITH tablename(columns...) AS (select_statement)
```





### Concept

> SQL执行流程

```sql
SELECT DISTINCT target-list
FROM single table
WHERE qualification
GROUP BY grouping list
HAVING group-qualification
```



1. FROM  确认要查询的表
2. WHERE  筛选满足条件的记录
3. SELECT  保留所需要(出现在SELECT/GROUP BY/ HAVING)的字段
4. GROUP BY  形成需要的组
5. HAVING  筛选满足条件的组
6. DISTINCT  去除冗余的记录



> Division

 查找拥有所有船只的船员 ==>  查找没有哪艘船不持有的船员

```sql
select s.sname
from Sailors s
where not exists (
	select B.bid
    from Boats b
    where not exists (
    	select R.id
        where R.bid = B.bid
        And R.sid = S.sid
    )
);
```



> NULL

NULL代表**不确定**

所有与NULL直接进行判定的操作都被判为False

聚合函数会忽略其所有操作的字段中的NULL值



## Hardware

### Disk

访问的方式不是通过指针解除引用，而是借助API

READ：将一页数据从磁盘移到内存中，然后通过内存的地址对磁盘进行访问

WRITE：将一页数据从内存写入磁盘

注意API调用的速度都非常慢，需要良好的规划。



> 访问一个页的时间消耗

查找：磁盘臂将磁头定位到目标位置

旋转延迟：等待目标数据块旋转到磁头下

传输时间：将数据从磁盘读取到内存

主要的IO时间消耗在查找和旋转延迟上



> 预测行为

缓存访问次数多的数据块

提前将很有可能被访问的数据提取到内存中

若要写入的是连续的数据块，则可以先进行缓存，最后一并写入。



> 磁盘中数据块的组织

Next block concept：

- 在同一磁道上连续的数据块
- 在同一磁盘上的数据块
- 在相邻磁盘上的数据块

因此可以将文件的数据块在磁盘上连续存储，来介绍查询和旋转延迟

对于顺序的扫描，就可以做到提前提取，一次读取大量连续的数据块（一次性将文件的大部分提取到内存中）



### Files

表会被存储为逻辑文件，由数据块构成，每个数据块中都存储一系列记录



>  DB File Structures

1. Unordered Heap Files

   记录在页中随意存放

2. Clustered Heap Files

   记录和页被分组存放

3. Sorted Files

   记录和页按某一顺序存放

4. Index Files

   可能会包含指向其他文件中记录的索引



### Page

Header会包含：

1. 记录数量
2. 空余的空间
3. 可能会有指向下一个元素的指针
4. 可能会有Bitmaps



> 布局

方向：

- 记录数量（定长还是变长）

- 是否打包

  

定长：

1. 打包

   让记录稠密分布，类似链表的形式。

   Record id = (pageId, record number in page)

   添加元素十分容易，但删除需要重组数据，让后面的数据填补被删数据的空白。

![image-20220528161445934](D:\develop\git\MyRemake\database\img\image-20220528161445934.png)

2. 不打包

   在头部添加bitmaps，标记每个存储单元的存储情况

   在插入时只要寻找首个还没被标记的存储单元即可

   删除时只需要清除bitmaps中对应的位即可

![image-20220528161648072](D:\develop\git\MyRemake\database\img\image-20220528161648072.png)



变长：

在page的底部维护一个slot directory，第一个元素（从右往左）为记录的个数，第二个为指向首个空地址的指针，其余位置放置各个记录的id。

插入元素时，只需要直接插入第二个指针指向的存储单元，并用一个闲置槽记录新纪录的id即可

删除元素时，回收对应的存储单元，并清除对应id所在的槽。

删除会导致page中出现不连续的空闲空间，此时可以对其进行再分配，也可以等剩余连续空间用完后再清理。

而当page大小需要变化时，例如page需要扩容，则直接在slot directory的最后添加记录id即可（从右往左看）

![image-20220528163256111](D:\develop\git\MyRemake\database\img\image-20220528163256111.png)