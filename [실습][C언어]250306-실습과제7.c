#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

void prob_7() {
	char gender;
	int age;
	double height;

	// 자료 입력 받기
	printf("\n성별은?(남자라면 M 여자라면 F)");
	scanf_s("%c", &gender, 1);

	printf("\n나이는?");
	scanf_s("%d", &age);

	printf("\n키는?");
	scanf_s("%lf", &height);


	// 결과 출력하기
	printf("\n==================");
	printf("\n성별: %c", gender);
	printf("\n나이: %d세", age);
	printf("\n키: %.1lfcm", height);
}
