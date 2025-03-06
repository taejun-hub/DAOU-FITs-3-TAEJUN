void prob_10_1() {
	int r;
	int menu;
	do {
		printf("\n반지름은? ");
		scanf_s("%d", &r);
		printf("\n===================");
		printf("\n 1. 원의 둘레 구하기");
		printf("\n 2. 원의 넓이 구하기");
		printf("\n 3. 원의 부피 구하기");
		printf("\n 4. 그만두기");
		printf("\n===================");
		printf("\n원하는 메뉴는? ");
		scanf_s("%d", &menu);

		switch (menu)
		{
		case 1:
			printf("\n반지름이 %d인 원의 둘레는 %.2lf", r, 2 * r * M_PI);
			break;
		case 2:
			printf("\n반지름이 %d인 원의 넓이는 %.2lf", r, r * r * M_PI);
			break;
		case 3:
			printf("\n반지름이 %d인 구의 부피는 %.2lf", r, 4 * r * r * r * M_PI);
			break;
		default:
			break;
		}
	} while (menu != 4);
}


void prob_10_2() {
	int n;
	int sum = 0;
	printf("\n정수 n을 입력 : ");
	scanf_s("%d", &n);

	for (int i = 1; i <= n; i++) {
		if (i % 2 == 0) {
			sum += i;
		}
	}
	printf("\n정수 1에서 50 이하 짝수들의 합은 %d입니다.", sum);
}
