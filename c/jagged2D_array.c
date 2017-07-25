#include<stdio.h>
#include<conio.h>
#include <stdlib.h>
int main()
{
    // 2d pointer array
    int **arr2d;
    // pointer array to store variable column length
    int *col_size_ptr;
    int row,col;
    int i,j;
    printf("Enter no of rows : ");
    scanf("%d",&row);
    arr2d =(int**)calloc(row,sizeof(int));
    col_size_ptr =(int*)calloc(row,sizeof(int));
    addLineSeparator();
    for(i=0;i<row;i++){
       printf("Enter size of %d array : ",i);
       scanf("%d",&col);
       col_size_ptr[i]=col;
       arr2d[i] =(int*)calloc(col,sizeof(int));
        for(j=0;j<col;j++){
            printf("Enter element at arr[%d][%d] : ",i,j);
            scanf("%d",&arr2d[i][j]);
        }
        addLineSeparator();
    }
    printf("2D array is \n");
    printarray(arr2d,col_size_ptr,row);
    getch();
    return 0;
}

void addLineSeparator(){
    printf("\n");
}

void printarray(int **arr2d,int *col_size_ptr,int r){
    int i,j;
    for(i=0;i<r;i++){
        for(j=0;j<col_size_ptr[i];j++){
            printf("%d ",*(*(arr2d+i)+j));
        }
        addLineSeparator();
    }
}

