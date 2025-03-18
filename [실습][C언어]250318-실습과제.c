#include <stdio.h>
#include <stdlib.h>

// 실습과제 38.
int* getStack() {
	int a = 1;
	return &a;
}

int* getHeap() {
	int* p = (int*)malloc(sizeof(int));
	*p = 2;
	return p;
}

void prob_38() {
	int* p1 = getStack();
	int* p2 = getHeap();
	printf("%d %d", *p1, *p2);
	free(p2);
}

// 실습과제 39.
void prob_39() {
	int* p1 = (int*)malloc(sizeof(int));
	int* p2 = (int*)calloc(1, sizeof(int));

	printf("%d %d", *p1, *p2);
	free(p1);
	free(p2);
}


// 실습과제 40.
void prob_40() {
	int* p1 = (int*)malloc(sizeof(int));
	*p1 = 3;

	int* p2 = (int*)realloc(p1, sizeof(int) * 4);

	printf("p1: %p %d\r\n", p1, *p1);
	printf("p1: %p %d\r\n", p2, *p2);

	//free(p1); // 런타임 에러
	free(p2);
}
