#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <errno.h>
#include <string.h>

// 실습과제 48.
void prob_48() {
	FILE* fp = fopen("TestFile.txt", "wb");
	char str[] = "C 프로그래밍";

	if (fp)
	{
		fwrite(str, sizeof(str) - 1, 1, fp);
		fclose(fp);
	}
	else
	{
		printf("Error:%d, %s", errno, strerror(errno));
	}
}

// 실습과제 49.
void prob_49() {
	FILE* fp = fopen("TestFile.txt", "wb+");
	if (fp)
	{
		fputc('A', fp);

		fseek(fp, 0, SEEK_SET);
		int c = fgetc(fp);
		printf("read: %c", c);

		fclose(fp);
	}
	else
	{
		printf("Error:%d, %s", errno, strerror(errno));

	}
}

// 실습과제 50.
void prob_50() {
	FILE* fp = fopen("TestFile.txt", "wb+");
	char str[128];
	if (fp)
	{
		fputs("0123456789\r\n0123456789", fp);

		fseek(fp, 0, SEEK_SET);
		fgets(str, sizeof(str), fp);
		printf("read: %s", str);

		fclose(fp);
	}
	else
	{
		printf("Error:%d, %s", errno, strerror(errno));

	}
}
