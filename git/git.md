# Git

## 1. 处理数据的方式

记录当前项目快照，而非像其他版本管理工具一样记录更改。

对于在没有改动的文件，会让其指向之前版本的对应文件。

![dealdata](D:\develop\git\MyRemake\git\images\dealdata.png)

> git中数据的三种状态

modified : 文件内容被修改，但还未被添加到暂存区

staged : 文件修改被提交到暂存区，下个版本会体现该修改

committed : 修改被提交到本地仓库



> git 存储数据的结构

Git 用 tree 来表示每一份项目快照。对于没有修改的文件，git不会重复存储。

git会保留每一个文件的所有版本

![object graph](D:\develop\git\MyRemake\git\images\object graph.png)

## 2. 基本用法

> git clone *URI-of-remote-repo* [*project-name*]

克隆远程仓库到本地



> git init

初始化本地仓库

--initial-branch 初始化分支

--bare 创建一个裸仓库（纯git目录，无法进行添加文件等操作，一般用于服务器）

--template 通过模板来构建自定义目录



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



> git fetch

 拉取远程某些分支的最新代码，不会执行merge操作



> git remote

配置远程仓库

git remote add 远程仓库名 [url]

git remote set-url --add --push 远程仓库名 [url]  修改指定远程仓库的push目的地



> git gc

删除一些不必要的object并进行压缩

git reflog expire --expire=now --all 取消操作记录日志

git gc --prune=now 让gc处理当前时间点前的object



> 密钥配置

ssh-keygen -t ed25519(加密算法) -C [email]



## 3.原理介绍

### 3.1 Objects

commit, tree, blob在git中统称为Object

blob：存储文件内容

Tree：存储文件目录信息

commit：存储提交信息，一个commit可以对应唯一的一个版本代码



### 3.2 Refs

refs的内容就是对应的commit ID

refs/head前缀表示的是分支



### 3.3历史版本

commit中有parent字段，可通过串联获取历史版本

> 修改历史版本

git commit --amend

修改上一次提交的message，只会新增一个commit object，相关的tree ,parent都不变

git fsck --lost-found查找悬空的commit（没有refs指向他）



### 3.4 代码合并

> Fast-Forward

不会产生一个merge节点，合并后保持线性历史，如果目标分支有更新，则要通过rebase操作更新源分支

git merge 分支名 --ff-only

![image-20220524231820369](D:\develop\git\MyRemake\git\images\fast-forward.png)



> Three way forward

三方合并，会产生一个merge节点。

git merge --no-ff

