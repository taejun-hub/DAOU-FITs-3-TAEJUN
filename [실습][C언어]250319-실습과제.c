#include <stdio.h>
#include <errno.h>
#include <string.h>

struct Flags {
	unsigned int isVisible : 1;
	unsigned int isActive : 1;
	unsigned int isDeleted : 1;
	unsigned int type : 2;
};

// 실습과제 42.
void prob_42() {
	struct Flags flag = { 1, 0, 0, 2 };

	printf("isVisible: %u\n", flag.isVisible);
	printf("isActive: %u\n", flag.isActive);
	printf("isDeleted: %u\n", flag.isDeleted);
	printf("type: %u\n", flag.type);

}

// 실습과제 44.
void prob_44() {
	FILE* fp = fopen("abc.txt", "r");
	char buf[30];
	if (fp)
	{
		fclose(fp);
	}
	else
	{
		strerror_s(buf, 30, errno);
		printf("Error: %d, %s", errno, buf);
	}
}
