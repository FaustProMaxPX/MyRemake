#ifndef QUEUE
#define QUEUE

#define ElemType int

typedef struct node Node;
struct node
{
    ElemType data;
    Node* next;
};


typedef struct queue
{
    Node* front;
    Node* tail;
    int size;
}*Queue;

void EnQueue(ElemType data, Queue queue);
ElemType DeQueue(Queue queue);
int isEmpty(Queue queue);
int size(Queue queue);

#endif