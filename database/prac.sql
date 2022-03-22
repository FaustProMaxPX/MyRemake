--一、单表
--（1）查询所有年龄大于等于20岁的学生学号、姓名；
SELECT sNo,sName 
FROM student 
WHERE age > 20;
--（2）查询所有姓钱的男生学号、姓名、出生年份；
SELECT sNo, sName, YEAR(date_sub(NOW(), interval age YEAR)) as '出生年份' 
FROM student
WHERE sName LIKE '钱%';
--（3）查询所有学分大于3的课程名称；
SELECT cName 
FROM course 
WHERE credit > 3;
--（4）查询所有没有被分配到任何学院的学生姓名；
SELECT sName 
FROM student 
WHERE dNo IS NULL;
--（5）查询所有尚未设置主页的学院名称。
SELECT dName 
FROM department 
WHERE homePage IS NULL;

--二、聚集
--（1）查询各个学院的平均年龄；
SELECT d.dName, AVG(s.age) as '平均年龄' 
FROM student s, department d 
WHERE s.dNo = d.dNo 
GROUP BY dName;
--（2）查询每个学生选修课程的平均分；
SELECT s.sName, AVG(sc.score) as 'average_score' 
FROM sc as sc, student s 
WHERE sc.sNo = s.sNo 
GROUP BY s.sName;
--（3）查询各课程的平均分；
SELECT c.cName, AVG(sc.score) as '平均分'
FROM sc
RIGHT JOIN course c
ON sc.cNo = c.cNo
GROUP BY c.cName;
--（4）查询各学院开设的课程门数；
SELECT d.dName, COUNT(c.cNo) as '开课门数' 
FROM course c, department d 
WHERE c.dNo = d.dNo 
GROUP BY dName;
--（5）查询各门课程选修人数。
SELECT c.cName as '课程名', COUNT(DISTINCT sc.sNo) as '人数'
FROM sc
RIGHT OUTER JOIN course c
ON sc.cNo = c.cNo
GROUP BY c.cName;
--三、多表
--（1）查询“信息学院”所有学生学号与姓名；
SELECT s.sNo as '学号', s.sName as '姓名' 
FROM student s
WHERE s.dNo = (
    SELECT dNo 
    FROM department d
    WHERE d.dName = '信息学院'
);
--（2）查询“软件学院”开设的所有课程号与课程名称；
SELECT c.cNo as '课程号', c.cName as '课程名称' 
FROM course c 
WHERE c.dNo = (
    SELECT d.dNo 
    FROM department d
    WHERE d.dName = '软件学院'
);
--（3）查询与“陈丽”在同一个系的所有学生学号与姓名；
SELECT s1.sNo as '学号', s1.sName as '姓名' 
FROM student s1
WHERE s1.dNo = (
    SELECT s2.dNo 
    FROM student s2
    WHERE s2.sName = '陈丽' 
) 
AND s1.sName != '陈丽';
--（4）查询与“张三”同岁的所有学生学号与姓名；
SELECT s1.sNo as '学号', s1.sName as '姓名' 
FROM student s1
WHERE s1.age = (
    SELECT s2.age 
    FROM student s2
    WHERE s2.sName = '张三'
) 
AND s1.sName != '张三';
--（5）查询与“张三”同岁且不与“张三”在同一个系的学生学号与姓名；
SELECT s1.sNo as '学号', s1.sName as '姓名'
FROM student s1
WHERE s1.age = (
    SELECT s2.age 
    FROM student s2
    WHERE s2.sName = '张三'
) 
AND s1.dNo <> (
    SELECT s3.dNo 
    FROM student s3
    WHERE s3.sName = '张三'
);
--（6）查询学分大于“离散数学”的所有课程名称；
SELECT c1.cName as '课程名称' 
FROM course c1
WHERE c1.credit > (
    SELECT c2.credit 
    FROM course c2
    WHERE c2.cName = '离散数学'
);
--（7）查询选修了课程名为“组合数学”的学生人数；
SELECT COUNT(DISTINCT sNo) AS '选课人数'
FROM sc
WHERE cNo = (
    SELECT c.cNo 
    FROM course c
    WHERE c.cName = '组合数学'
);
--（8）查询没有选修“离散数学”的学生姓名；
SELECT s.sName as '姓名'
FROM student s
WHERE NOT EXISTS (
    SELECT * 
    FROM sc
    WHERE sc.cNo = (
        SELECT c.cNo
        FROM course c
        WHERE cName = '离散数学'
    ) 
    AND s.sNo = sc.sNo
);
--（9）查询与“算法设计与分析”、“移动计算”学分不同的所有课程名称；
SELECT c1.cName as '课程名称'
FROM course c1
WHERE c1.credit NOT IN (
    SELECT c2.credit
    FROM course c2
    WHERE c2.cName = '算法设计与分析'
    OR c2.cName = '移动计算'
);
--（10）查询平均分大于等于90分的所有课程名称；
SELECT c.cName as '课程名称'
FROM course c
WHERE c.cNo = (
    SELECT sc.cNo
    FROM sc
    GROUP BY sc.cNo
    HAVING AVG(sc.score) >= 90
);
--（11）查询选修了“离散数学”课程的所有学生姓名与成绩；
SELECT s.sName as '姓名', sc.score as '分数' 
FROM student s, sc
WHERE s.sNo = sc.sNo
AND sc.cNo = (
    SELECT c.cNo
    FROM course c
    WHERE c.cName = '离散数学'
);
--（12）查询“王兵”所选修的所有课程名称及成绩；
SELECT c.cName as '课程名', sc.score as '成绩'
FROM course c, sc
WHERE c.cNo = sc.cNo
AND sc.sNo IN (
    SELECT s.sNo
    FROM student s
    WHERE s.sName = '王兵'
);
--（13）查询所有具有不及格课程的学生姓名、课程名与成绩；
SELECT s.sName as '学生姓名', c.cName as '课程名', sc.score as '成绩'
FROM student s, course c, sc
WHERE s.sNo = sc.sNo 
AND sc.cNo = c.cNo 
AND sc.score < 60 ;
--（14）查询选修了“文学院”开设课程的所有学生姓名；
SELECT s.sName as '学生姓名'
FROM student s
WHERE s.sNo IN (
    SELECT distinct sc.sNo
    FROM sc, course c
    WHERE sc.cNo = c.cNo
    AND c.dNo = (
        SELECT d.dNo
        FROM department d
        WHERE d.dName = '文学院'
    )
);
--（15）查询“信息学院”所有学生姓名及其所选的“信息学院”开设的课程名称。
SELECT s.sName, tmp.cName
FROM student s
LEFT OUTER JOIN (
    SELECT c.cName, sc.sNo, d.dNo
    FROM course c, sc, department d
    WHERE d.dName = '信息学院' AND c.dNo = d.dNo AND sc.cNo = c.cNo
) tmp
ON s.sNo = tmp.sNo AND tmp.dNo = s.dNo;
--四、综合
--（1）查询所有学生及其选课信息（包括没有选课的学生）；
SELECT s.sNo as '学号',s.sName as '学生姓名', tmp.cNo as '课程号',tmp.cName as '课程名'
FROM student s 
LEFT OUTER JOIN (
    SELECT c.cNo, c.cName, sc.sNo
    FROM course c, sc
    WHERE c.cNo = sc.cNo
) tmp
ON s.sNo = tmp.sNo;
--（2）查询“形式语言与自动机”先修课的课程名称；
SELECT c1.cName as '课程名'
FROM course c1
WHERE c1.cNo = (
    SELECT c2.cPNo
    FROM course c2
    WHERE c2.cName = '形式语言与自动机'
);
--（3）查询“形式语言与自动机”间接先修课课程名称；
SELECT c1.cName as '课程名'
FROM course c1
WHERE c1.cNo IN (
    SELECT c2.cPNo
    FROM course c2
    WHERE c2.cNo = (
        SELECT c3.cPNo
        FROM course c3
        WHERE c3.cName = '形式语言与自动机'
    )
);
--（4）查询先修课为编译原理数学的课程名称；
SELECT c1.cName
FROM course c1
WHERE c1.cPNo = (
    SELECT c2.cNo
    FROM course c2
    WHERE c2.cName = '编译原理'
);
--（5）查询间接先修课为离散数学的课程名称；
SELECT c1.cName
FROM course c1
WHERE c1.cPNo IN (
    SELECT c2.cNo
    FROM course c2
    WHERE c2.cPNo = (
        SELECT c3.cNo
        FROM course c3
        WHERE c3.cName = '离散数学'
    )
);
--（6）查询所有没有先修课的课程名称；
SELECT c.cName
FROM course c
WHERE c.cPNo IS NULL;
--（7）查询所有没选修“形式语言与自动机”课程的学生姓名；
SELECT s.sName AS '学生姓名'
FROM student s
WHERE s.sNo NOT IN (
    SELECT sc.sNo
    FROM sc, course c
    WHERE c.cName = '形式语言与自动机' AND sc.cNo = c.cNo
);
--（8）查询仅仅选修了离散数学一门课程的学生姓名；
SELECT s.sNo,s.sName
FROM student s
WHERE s.sNo NOT IN (
    SELECT sc.sNo
    FROM sc
    WHERE sc.cNo <> (
        SELECT c.cNo
        FROM course c
        WHERE c.cName = '离散数学'
    )
)
AND s.sNo IN (
    SELECT sc.sNo
    FROM sc
);
--（9）查询所有选修了“形式语言与自动机”但没选修其先修课的学生姓名；
SELECT s.sName as '学生姓名'
FROM student s
WHERE s.sNo IN (
    SELECT sc.sNo
    FROM sc, course c
    WHERE c.cName = '形式语言与自动机' AND sc.cNo = c.cNo
)
AND s.sNo NOT IN (
    SELECT sc.sNo
    FROM sc, course c
    WHERE sc.cNo = c.cNo AND c.cNo = (
        SELECT c2.cPNo
        FROM course c2
        WHERE c2.cName = '形式语言与自动机'
    )
);
--（10）查询选修课程总学分大于等于28的学生姓名及其选修课程总学分；
SELECT s.sName as '姓名', SUM(c.credit) as '总学分'
FROM student s, sc, course c
WHERE s.sNo = sc.sNo AND sc.cNo = c.cNo
GROUP BY sc.sNo
HAVING SUM(c.credit) >= 28;
--（11）查询选修了3门以上课程且成绩都大于85分的学生学号与姓名；
SELECT s.sNo, s.sName
FROM student s, sc
WHERE s.sNo = sc.sNo AND sc.score IS NOT NULL
GROUP BY s.sNo
HAVING COUNT(sc.cNo) > 3 AND MIN(sc.score) > 85;
--（12）查询恰好选修了3门课并且都及格的学生姓名；
SELECT s.sName
FROM student s, sc
WHERE s.sNo = sc.sNo AND sc.score IS NOT NULL
GROUP BY s.sNo
HAVING COUNT(sc.cNo) = 3 AND MIN(sc.score) > 60;
--（13）查询人数多于6的学院名称及其学生人数；
SELECT d.dName as '学院名', COUNT(s.sNo) as '学生数'
FROM student s, department d
WHERE s.dNo = d.dNo AND s.dNo IS NOT NULL
GROUP BY d.dNo
HAVING COUNT(s.sNo) > 6;
--（14）查询平均成绩高于王兵的学生姓名；
SELECT s.sName
FROM student s, sc
WHERE s.sNo = sc.sNo
GROUP BY sc.sNo
HAVING AVG(sc.score) > (
    SELECT AVG(sc2.score)
    FROM student s2, sc sc2
    WHERE s2.sNo = sc2.sNo AND s2.sName = '王兵' AND sc2.score IS NOT NULL
    
);
--（15）查询所有选修了离散数学并且选修了编译原理课程的学生姓名；
SELECT s1.sName
FROM student s1
WHERE s1.sNo IN (
    SELECT sc.sNo
    FROM course c1, sc
    WHERE c1.cNo = sc.cNo AND c1.cName = '离散数学'
)
AND s1.sNo IN (
    SELECT sc.sNo
    FROM course c2, sc
    WHERE c2.cNo = sc.cNo AND c2.cName = '编译原理'
);
--（16）查询软件学院离散数学课程平均分；
SELECT AVG(sc.score)
FROM student s, sc
WHERE s.sNo = sc.sNo AND s.dNo = (
    SELECT d.dNo
    FROM department d
    WHERE d.dName = '软件学院'
);
--（17）查询年龄与“软件学院”所有学生年龄都不相同学生姓名及其年龄和学院；
SELECT s.sName, s.age, d.dName 
FROM student s
LEFT OUTER JOIN department d
ON s.dNo = d.dNo
WHERE s.age NOT IN (
    SELECT s2.age
    FROM student s2, department d2
    WHERE s2.dNo = d2.dNo AND d2.dName = '软件学院' AND s2.age IS NOT NULL
);
--（18）查询各学院选修同一门课人数大于4的学院、课程及选课人数；
SELECT d.dName, c.cName, COUNT(sc.sNo)
FROM student s, sc, course c, department d
WHERE s.sNo = sc.sNo AND sc.cNo = c.cNo AND s.dNo = d.dNo
GROUP BY sc.cNo, d.dNo
HAVING COUNT(sc.sNo) > 4;

--（19）查询仅仅选修了“高等数学”一门课程的学生姓名；（学号、姓名及所在学院名称）
SELECT s.sNo, s.sName, d.dName
FROM student s
LEFT OUTER JOIN department d
ON s.dNo = d.dNo
WHERE s.sNo NOT IN (
    SELECT sc.sNo
    FROM sc, course c
    WHERE sc.cNo = c.cNo AND c.cName <> '高等数学'
)
AND s.sNo IN (
    SELECT sc.sNo
    FROM sc
);

--（20）查询平均学分积小于70分的学生姓名。

SELECT s.sName, SUM(sc.score * c.credit) / SUM(c.credit)
FROM student s, sc, course c
WHERE s.sNo = sc.sNo AND sc.cNo = c.cNo AND sc.score IS NOT NULL
GROUP BY s.sNo
HAVING SUM(sc.score * c.credit) / SUM(c.credit) < 70.0;

--（21）查询选修了“信息学院”开设全部课程的学生姓名。

SELECT s.sName
FROM student s
WHERE NOT EXISTS (
    SELECT *
    FROM course c, department d
    WHERE c.dNo = d.dNo
    AND d.dName = '信息学院'
    AND c.cNo NOT IN (
        SELECT sc.cNo
        FROM sc
        WHERE sc.sNo = s.sNo
    )
);
--（21）查询选修了“杨佳伟”同学所选修的全部课程的学生姓名。
SELECT s.sName
FROM student s
WHERE NOT EXISTS (
    SELECT *
    FROM student s1, sc
    WHERE s1.sNo = sc.sNo
    AND s1.sName = '杨佳伟'
    AND sc.cNo NOT IN (
        SELECT sc.cNo
        FROM sc
        WHERE sc.sNo = s.sNo
    )
);
