#include <stdio.h>
#include "queue.h"
#include <stdlib.h>

Queue init();

int main()
{
    Queue queue = init();
    for (int i = 0; i < 10; i++)
    {
        EnQueue(i, queue);
    }
    printAndClean(queue);
    
    return 0;
}

Queue init()
{
    Queue queue = (Queue) malloc (sizeof(struct queue));
    queue->front = queue->tail = (Node*) malloc(sizeof(Node));
    return queue;
}

void EnQueue(ElemType data, Queue queue)
{
    Node* node = (Node*) malloc (sizeof(Node));
    node->data = data;
    node->next = NULL;
    queue->tail->next = node;
    queue->tail = node;
    queue->size++;
}

int isEmpty(Queue queue)
{
    return queue->front == queue->tail;
}

ElemType DeQueue(Queue queue)
{
    if (isEmpty(queue))
    {
        printf("Queue is empty");
        return;
    }
    Node* node = queue->front->next;
    ElemType data = node->data;
    queue->front->next = node->next;
    if (queue->front->next == NULL)
        queue->tail = queue->front;
    free(node);
    queue->size--;
    return data;
}

int size(Queue queue)
{
    return queue->size;
}

void printAndClean(Queue queue)
{
    while (!isEmpty(queue))
    {
        ElemType data = DeQueue(queue);
        printf("$%d ", data);
    }
    printf("\n====================\n");
}