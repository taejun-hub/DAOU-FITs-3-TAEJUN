#include <stdio.h>
#include <stdlib.h>

// 실습과제: 이차원 배열 동적 할당 예제
void malloc_prac3() {
	int rows = 3, col = 4;
  
  // 2차원 배열 동적할당
	int** arr = (int **)calloc(rows, sizeof(int *));
	for (int i = 0; i < rows; i++)
	{
		arr[i] = (int *)calloc(col, sizeof(int));
	}

	for (int i = 0; i < rows; i++)
	{
		for (int j = 0; j < col; j++)
		{
			arr[i][j] = i * col + j;
			printf("%d\t", arr[i][j]);
		}
		printf("\n");
	}
  
  // 메모리 해제
	for (int i = 0; i < rows; i++)
	{
		free(arr[i]);
	}
	free(arr);
}

int main()
{
	malloc_prac3();
}


