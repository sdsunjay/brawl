
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
int main(void)
{
//loop counter
   int num;
//length of stdin string
int length;
//pointer to file specified by user
FILE* fp;
char c=0;
fp=fopen("gasp","r");

while((c=fgetc(fp))!=-1)
{
   if((int)c>0 &&(int)c<=9);
   printf("%d\n",c);
}
return 0;
}
