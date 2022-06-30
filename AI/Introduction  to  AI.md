# Introduction  to  AI



## 术语

1. actions

   在某一状态下可以作出的选择

   ACTION(s) 返回在状态s下可以采取的行动集合。

2. transition model

   针对某个状态采取某个动作后产生的结果

   RESULT(s, action)返回由当前状态采取某行动后生成的新模型。

3. state space

   从初始状态开始经过一系列行为可能产生的状态。



## Search

search problems contain

1. initial state
2. actions
3. transition model
4. goal test 测试给定的状态是否为目标状态
5. path cost function 代价计算



### Node

是一种记录当前状态信息的数据结构，其中包含：

1. 当前状态描述
2. 父状态
3. 由父状态转移到当前状态采取的行动
4. 到达当前状态的消耗



### MiniMax

进行二元博弈时，做最坏的打算，尽最大的努力。

博弈结果用分数来表示，A胜加分，B胜减分。一方尽可能的使分数增大，一方尽可能的使分数减少。

由于任何一方在做决策时都会受到另一方之前所作决策的影响。例如A要在B尽可能降低了分数的情况下，作出最大程度提高分数的决策。

```python
def MAX_VALUE(state):
    # 若当前状态对局已结束，则返回对局结果
    if TERMINAL(state):
        return UTILITY(state)
    # 初始化当前分数为负无穷
    v = -INFINITY
    # 尝试所有可以由当前状态派生出的状态，计算对手在派生状态中采取的降低分数的决策。选取所有派生中仍旧能带来最大收益的状态。
    for action in ACTIONS(state):
        v = MAX(v, MIN_VALUE(RESULT(state, action)))
    return v
```

如此递归的求解可能的状态，效率并不高，因此可以做一定的剪枝操作，例如当派生状态1的最小收益为4时，若派生状态2已经确定其最小值小于4（孙子状态有收益值已经小于4了，则对手采取操作后该派生的最大收益必定不可能大于4）。此时可以直接忽略该状态。

Limit Minimax

限制minimax的求解次数，例如限制搜索树的深度，每一步决策只尝试计算之后X步的可能状态。同时添加一个估计函数，判断达成目标的可能性。



## Knowledge

model:负责对所有可能的逻辑语句作出判断

knowledge base: 计算机初始情况下已知正误的逻辑语句集合

model check：依据已有的知识来推断给定的语句是否成立。罗列所有的可能性，判断何时前置条件与查询结果均成立。

```python
def model_check(knowledge, qeury):
    
    def check_all(knowledge, query, symbols, model):
        # all of the symbols have been assigned
        if not symbols:
            # if this model is compatiable with knowledge
            # return whether query is compatiable with model
            if knowledge.evaluate(model):
                return query.evaluate(model)
            # else this model is nonsense to us
            return True
        else:
            remaining = symbols.copy()
            # pop a symbol to assign
            p = remaining.pop()
            # 分两种情况讨论，当前条件为真或当前条件为假
            model_true = model.copy()
            model_true[p] = True
            model_false = model.copy()
            model_false[p] = False
            # 如果两种情况无法同时为真，就代表还有其他条件影响结果，此时不能得出结论。但若同时为真，就代表无论其余条件是什么，目前已有知识已经足够作出判断
            return check_all(knowledge, query, remaining, model_true) && chekc_all(knowledge, query, remaining, model_false)
    
    symbols = set.union(knowledge, query)
    check_all(knowledge, query, symbols, dict())
```

