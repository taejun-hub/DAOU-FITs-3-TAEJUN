#include <stdio.h>
#include "prob.h"
#define N 5
#define SIZE 5

void prob_16() {
	int i, repeat, temp, b[SIZE] = { 1, 2, 3, 4, 5 };

	char swap;

	for (repeat = 1; repeat < SIZE; repeat++) {
		swap = 'N';
		for (i = 0; i < SIZE - repeat; repeat++) {
			if (b[i] > b[i + 1]) {
				temp = b[i];
				b[i] = b[i + 1];
				b[i + 1] = temp;
				swap = 'Y';
			}
		}
		if (swap == 'N') break;
	}

	printf("\n정렬 후 배열: ");
	for (int i = 0; i < SIZE; i++) printf("%4d", b[i]);
	printf("\n\n");

}
