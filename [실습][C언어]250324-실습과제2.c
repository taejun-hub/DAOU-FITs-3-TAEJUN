#include <stdio.h>

// 실습과제 53.
void prob_53() {
	#define ONE
	#ifndef ONE
		int a = 1;
	#else
		int a = 2;
	#endif // !ONE
		printf("a: %d", a);
}

// 실습과제 54.
void prob_54() {
	#define X 1
	#if (X == 1)
		printf("X is 1");
	#elif (X == 2)
		printf("X is 2");
	#else
		printf("X is nothing");
	#endif
}
