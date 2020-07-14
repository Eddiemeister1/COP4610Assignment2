#include "syscall.h"
#include "stdio.h"
#include "stdlib.h"
int
main(void)
{
    int fileDes;
    fileDes = creat("testCreate.txt");
    if(fileDes == -1)
    {
	printf("Error: Failed to create file");
	return 1;
    }
    /* not reached */
}
