#include <stdio.h>


int main() {
	printf("%c\n", 'a');
	printf("%c\n", 97);
	printf("%c\n", '\141');
	printf("%c\n", '\x61');


	printf("삐음: %c\n", '\a');
	printf("단일 인용부호: %c\n", '\'');
	printf("abcdef\b\b\b\b");
	printf("%s\n", "ghijk");

	return 0;
}
