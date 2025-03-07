void prob11_1() {
	int a, b;

	printf("\n가감승제를 원하는 두 수를 입력하세요: ");
	scanf_s("%d %d", &a, &b);

	printf("\n%d + %d = %d", a, b, a + b);
	printf("\n%d - %d = %d", a, b, a - b);
	printf("\n%d * %d = %d", a, b, a * b);
	printf("\n%d / %d = %lf", a, b, (double)a / (double)b);
}

void prob11_2() {
	int n;

	printf("\n출력을 원하는 구구단 단을 입력하세요: ");
	scanf_s("%d", &n);
	for (int i = 1; i < 10; i++)
	{
		printf("\n%d * %d = %d", n, i, n * i);
	}
}

void prob11_3() {
	char str[10];

	printf("\n문자열 입력? ");
	gets_s(str, sizeof(str));

	int i = 0;
	while (str[i] != NULL) {
		if (str[i] >= 97 && str[i] <= 122) {
			str[i] -= 32;
		}
		else {
			str[i] += 32;
		}
		i++;
	}
	printf("\n변환된 결과: %s", str);

}
