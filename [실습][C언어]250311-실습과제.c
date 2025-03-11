#include <stdio.h>

// 실습과제 22.
void prob_22() {
	double d = 100.0;
	double* dpoint = &d;
	printf("\n변수 d의 값: %lf", d);
	printf("\n변수 d의 주소 값: %p", &d);
	printf("\ndpoint(포인터 변수)의 값: %p", dpoint);
	printf("\ndpoint가 가리키는 값: %lf", *dpoint);
	printf("\n변수 d의 크기: %zd", sizeof(d));
	printf("\n변수 d의 주소의 크기: %zd", sizeof(&d));
	printf("\n포인터 변수 dpoint의 크기: %zd", sizeof(dpoint));
	printf("\n포인터 변수 dpoint가 가리키는 값의 크기: %zd", sizeof(*dpoint));

}

// 실습과제 23.
void prob_23() {
	int i, * ip = &i;
	int sum = 0, * sump = &sum;
	for (*ip = 1; *ip < 100; (*ip)++)
	{
		*sump += *ip;
	}
	printf("\n포인터 변수를 사용한 1 ~ 100까지의 합은: %d", sum);
}

// 실습과제 24.
void prob_24() {
	int score = 100;
	int *pscore, ** ppscore, *** pppscore;
	pscore = &score;
	ppscore = &pscore;
	pppscore = &ppscore;

	printf("\n포인터 변수 *pscore의 값은 %d", *pscore);
	printf("\n포인터 변수 **ppscore의 값은 %d", **ppscore);
	printf("\n포인터 변수 ***pppscore의 값은 %d", ***pppscore);
	printf("\n==================================");

	printf("\nint 변수 score의 주소는 %p", &score);
	printf("\nint 변수 pscore의 값(주소)는 %p", pscore);
	printf("\n==================================");

	printf("\n포인터 변수 pscore의 주소는 %p", &pscore);
	printf("\n포인터 변수 ppscore의 값(주소)는 %p", ppscore);
	printf("\n==================================");

	printf("\n포인터 변수 ppscore의 주소 %p", &ppscore);
	printf("\n포인터 변수 pppscore의 값(주소)은 %p", pppscore);
}

// 실습과제 25.
void prob_25() {
	int score[6] = { 95, 89, 98, 88, 85 };
	int *pscore, i, sum = 0;
	pscore = score;

	if (pscore + 2 == score + 2 && pscore + 2 == &score[2]) printf("\n두 번째 요소의 주소가 모두 같다");
	if (*(pscore + 2) == *(score + 2) && *(pscore + 2) == score[2]) printf("\n두 번째 요소의 값도 모두 같다");

	printf("\n포인터 변수 pscore의 값(배열 주소): %p", pscore);
	printf("\n배열 이름 score(배열 주소)의 값: %p", score);
	printf("\n포인터 변수 pscore의 값(배열 주소)의 주소: %p", &pscore);
	printf("\n배열 이름 score의 값(배열 주소)의 주소: %p", &score);

	sum = 0;
	for (i = 0; i < 5; i++) sum = sum + *pscore++;
	printf("\n포인터 변수를 이용한 합계는 %d", sum);
}
