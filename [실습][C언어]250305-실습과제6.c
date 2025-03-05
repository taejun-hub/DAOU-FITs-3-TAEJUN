int main() {
	char gender;
	int age;
	double height;
	printf("\n성별은?(남자라면 M 여자라면 F)");
	scanf_s("%c", &gender, 1);
	printf("\n나이는?");
	scanf_s("%d", &age);

	printf("\n키는?");
	scanf_s("%lf", &height);



	printf("\n==================");
	printf("\n성별: %c", gender);
	printf("\n나이: %d세", age);
	printf("\n키: %.1lfcm", height);


	return 0;
}
