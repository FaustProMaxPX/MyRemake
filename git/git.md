# Git

## 1. 处理数据的方式

记录当前项目快照，而非像其他版本管理工具一样记录更改。

对于在没有改动的文件，会让其指向之前版本的对应文件。

![dealdata](/home/faust/remake/git/images/dealdata.png)



> git中数据的三种状态

modified : 文件内容被修改，但还未被添加到暂存区

staged : 文件修改被提交到暂存区，下个版本会体现该修改

committed : 修改被提交到本地仓库



> git 存储数据的结构

Git 用 tree 来表示每一份项目快照。对于没有修改的文件，git不会重复存储。

git会保留每一个文件的所有版本

![object graph](/home/faust/remake/git/images/object graph.png)



## 2. 基本用法

> git clone *URI-of-remote-repo* [*project-name*]

克隆远程仓库到本地



> git diff

查看修改的内容(不包含在暂存区的修改)

--staged 包含在暂存区的修改



> git show [id : filename]

显示历史版本/指定版本信息，同时显示文件被修改的具体内容。

类似于 git log -p



> git status

查看当前版本的详细情况



> git stash

将当前修改保存，并恢复到修改之前的状态

git stash list 查看当前保存的修改

git stash pop 弹出保存的修改，并于后来进行的修改合并



> git pull

获取远程仓库的修改，并将其与本地仓库最新版本合并



> git merge

合并修改

若出现合并冲突，使用`git status`查看冲突文件并手动处理

解决冲突后要将文件重新提交到本地仓库

--continue 继续合并  --abort 抛弃本次合并



> git reset

取消文件的暂存状态

git reset HEAD filename



> git restore

git restore filename 丢弃工作区更改

git restore --staged filename 取消暂存

