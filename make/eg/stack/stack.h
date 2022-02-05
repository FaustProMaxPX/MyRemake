#ifndef STACK
#define STACK

#define ElemType int
typedef struct node Node;
struct node
{
    ElemType data;
    Node* next;
};

typedef struct stack
{
    Node* top;
}*Stack;


ElemType pop(Stack stack);
void push(Stack stack, ElemType data);
void printAndClean(Stack stack);

#endif