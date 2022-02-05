#include <stdio.h>
#include "def.h"

int main()
{
    int ans = sum(1,2);
    printf("%d\n", ans);
    printf("%d\n", global);
    printf("==============");
}

int sum(int a, int b)
{
    return a + b;
}
