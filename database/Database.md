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