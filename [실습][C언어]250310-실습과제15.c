#include <stdio.h>
#include "prob.h"
#define N 5
#define SIZE 5

void prob_15() {
	int f[N] = { 3, 0, -30, -20, -1 };
	int i, min;

	min = f[0];
	for (int i = 0; i < N; i++) min = f[i] < min ? f[i] : min;
	
	printf("\n어는 점 목록");
	for (int i = 0; i < N; i++) printf("%4d", f[i]);
	
	printf("\n가장 낮은 어는 점: %d\n", min);
}
