#include <stdio.h>
#define B_SIZE 8

// 실습과제 28.
void prob_28() {
	struct game
	{
		char name[B_SIZE];
		int R1, R2, R3;
	};

	struct game player;
	double avg;

	printf("\n선수의 이름? ");
	gets_s(player.name, B_SIZE);

	printf("1, 2, 3라운드 점수는? ");
	scanf_s("%d %d %d", &player.R1, &player.R2, &player.R3);

	avg = (double)(player.R1 + player.R2 + player.R3) / 3;

	printf("\n%s선수의 게임 결과 평균 %.1lf점", player.name, avg);
}


// 실습과제 29.
void prob_29() {
	struct student
	{
		char name[20];
		char sex;
		int stid, sub1, sub2, sub3;
		double avg;

	} st1 = { "kdhong", 'm', 1508001, 98, 89, 92, 0.0 }, st2;

	st1.avg = (double)(st1.sub1 + st1.sub2 + st1.sub3) / 3;

	st2 = st1;

	strcpy(st2.name, "yhkim");
	st2.sex = 'f';
	st2.stid = 1608024;

	printf("\n%s님(성별: %s)의 학번은 %d이고,\n이번 시험 3과목의 평균은 %5.2f입니다", st2.name, st2.sex == 'm' ? "남자" : "여자", st2.stid, st2.avg);
}
