#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

int main()
{
    Stack stack = (Stack) malloc (sizeof(struct stack));
    stack->top = NULL;
    for(int i = 0; i < 10; i++)
    {
        push(stack, i);
    }
    printAndClean(stack);
    return 0;
}

ElemType pop(Stack stack)
{
    if(stack == NULL || stack->top == NULL)
        return;
    ElemType ans = stack->top->data;
    Node* del = stack->top;
    stack->top = stack->top->next;
    free(del);
    return ans;
}

void push(Stack stack, ElemType data)
{
    if(stack == NULL)
        return;
    Node* node = (Node*) malloc (sizeof(Node));
    node->data = data;
    node->next = stack->top;
    stack->top = node;
}

void printAndClean(Stack stack)
{
    if(stack == NULL)
        return;
    while (stack->top != NULL)
    {
        ElemType data = pop(stack);
        printf("%d\n", data);
    }
    
}