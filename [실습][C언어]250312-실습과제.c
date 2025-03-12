#include <stdio.h>

// 실습과제 26.
void prob_26() {
	int sum = 10, score[] = { 99, 80, 91, 78, 85 };
	int* psum, * pscore1, * pscore2;

	psum = &sum;
	pscore1 = score;
	pscore2 = score;
	++pscore2;
	pscore2++;

	printf("\n변수 sum의 값: %d", sum);
	printf("\n포인터 변수 psum이 가리키는 값: %d", *psum);
	printf("\n포인터 변수 *psum + 1의 값: %d", *psum + 1);
	printf("\n포인터 변수 ++*psum의 값: %d", ++*psum);
	printf("\n포인터 변수 *(psum + 1)의 값: %d", *(psum + 1));
	printf("\n포인터 변수 *++psum의 값: %d", *++psum);
	printf("\n==================================");
	printf("\n포인터 변수 *pscore1의 값: %d", *pscore1);
	printf("\n포인터 변수 *pscore1 + 1의 값: %d", *pscore1 + 1);
	printf("\n포인터 변수 *(pscore1 + 1)의 값: %d", *(pscore1 + 1));
	printf("\n포인터 변수 *pscore2 - *pscore1의 값: %d", *pscore2 - *pscore1); 
	printf("\n포인터 변수 pscore2 - pscore1의 값: %d", pscore2 - pscore1);
	printf("\n포인터 변수 pscore2의 값: %p", pscore2);
	printf("\n포인터 변수 pscore1의 값: %p", pscore1); 
}





// 실습과제 27.
int max(int x, int y) {
	return x > y ? x : y;
}

int min(int x, int y) {
	return x < y ? x : y;
}
// 함수 포인터 타입 정의
typedef int (*Operation)(int, int);

void prob_27() {
	Operation op;
	int num1, num2, flag;

	printf("\n두 개의 숫자를 입력 : ");
	scanf_s("%d %d", &num1, &num2);

	printf("\n원하는 값을 입력(1: 큰값, 2: 작은값): ");
	scanf_s("%d", &flag);

	if (flag == 1)
	{
		op = max;
		printf("\n두 수중 큰 값은: %d", op(num1, num2));
	}
	else if (flag == 2)
	{
		op = min;
		printf("\n두 수중 작은 값은: %d", op(num1, num2));
	}
	else
	{
		printf("\n잘못된 숫자 입력입니다.");
	}

}
