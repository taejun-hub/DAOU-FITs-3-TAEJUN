#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#define SIZE 3
#define ROW 3
#define COL 3
#define B_SIZE 50


// 추가 문제 1.
void ptr_ex1() {
	int col, row, target_row;
	int arr[5][5];

	printf("\n행과 열의 수를 입력하세요 ? ");
	scanf_s("%d %d", &row, &col);

	printf("\n배열의 초기값는 자동으로 입력됩니다.\n");
	for (int i = 0; i < row; i++)
	{
		for (int j = 0; j < col; j++) 
		{
			arr[i][j] = i * 10 + j;
			printf("%3d", arr[i][j]);
		}
		printf("\n");
	}

	printf("\n삭제하려는 행의 인덱스 번호를 입력하세요? ");
	scanf_s("%d", &target_row);
	for (int i = target_row; i < row - 1; i++) for (int j = 0; j < col; j++) arr[i][j] = arr[i + 1][j];

	printf("\n수정된 배열 출력\n");
	row -= 1;
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) printf("%3d", arr[i][j]);
		printf("\n");
	}
}


// 추가 문제 2.
void arr_sum(int **pp) {
	int sum = 0;
	for (int i = 0; i < ROW; i++) for (int j = 0; j < COL; j++) sum += pp[i][j];
	printf("\n배열의 합은 %d", sum);
}

void arr_max(int **pp) {
	int max = -100000;
	for (int i = 0; i < ROW; i++) for (int j = 0; j < COL; j++) max = pp[i][j] > max ? pp[i][j] : max;
	printf("\n배열의 최대값은 %d", max);
}

void arr_min(int **pp) {
	int min = 100000;
	for (int i = 0; i < ROW; i++) for (int j = 0; j < COL; j++) min = pp[i][j] > min ? pp[i][j] : min;
	printf("\n배열의 최소값은 %d", min);
}

void arr_square(int* pp[]) {
	for (int i = 0; i < ROW; i++) for (int j = 0; j < COL; j++) pp[i][j] *= pp[i][j];
	printf("\n배열의 제곱값은\n");
	for (int i = 0; i < ROW; i++)
	{
		for (int j = 0; j < COL; j++) printf("%4d", pp[i][j]);
		printf("\n");
	}

}

void ptr_ex2() {
	void (*operations[4]) (int **) = { arr_sum, arr_max, arr_min, arr_square };
	int choice, array[ROW][COL] = {
		{0, 1, 2},
		{3, 4, 5},
		{6, 7, 8}
	};

	int* ptr_arr[ROW];
	for (int i = 0; i < ROW; i++) ptr_arr[i] = array + i;

	printf("\n연산 방법을 선택하기");
	printf("\n(0: 합, 1 : 최대값, 2 : 최소값, 3 : 제곱): ");
	scanf_s("%d", &choice);
	operations[choice]((int **)ptr_arr);

}
