#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

void prob_6_1() {
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


void prob_6_2() {

	// 배열 => 같은 자료형을 연속 기억 공간에 저장 
	char name[10];
	printf("이름은? ");
	// gets_s(name, 10);
	// 배열명은 배열의 시작 주소
	scanf_s("%s", name, 10);
	printf("\n입력한 이름: %s", name);
}

void prob_6_3() {
	int a;
	int b;
	int c;


	printf("\n여러 개의 정수 입력받기 >>");
	scanf_s("%d %d %d", &a, &b, &c);

	printf("\n입력받은 정수 %d, %d, %d", a, b, c);

}
