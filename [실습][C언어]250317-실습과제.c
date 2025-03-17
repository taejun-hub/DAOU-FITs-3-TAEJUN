#include <stdio.h>
#include <time.h>
#define MAX 100
#define MIN 0

// 실습과제 31.
int input_by_reference(ST *st) {
	printf("\n%s 학생의 3과목 성적을 입력하세요(공란으로 구분): ", st->name);
	scanf_s("%d %d %d", &st->sub1, &st->sub2, &st->sub3);
	printf("\ninput() 함수에서 입력된 값은 %d %d %d입니다", st->sub1, st->sub2, st->sub3);
}

void prob_31() {
	int i;
	ST st[3] = { {"kdhong", 'm', 1508001, 0, 0, 0, 0.0}, { "yhkim", 'f', 1608021, 0, 0, 0, 0.0 }, { "cskim", 'm', 1608026, 0, 0, 0, 0.0 } };
	for (i = 0; i < 3; i++)
	{
		input_by_reference(&st[i]);
		printf("\nmain() 함수에서 출력된 값은 %d %d %d입니다", st[i].sub1, st[i].sub2, st[i].sub3);

	}
}

// 실습과제 34.
void prob_34() {
	time_t now;
	struct tm t;
	
	time(&now);
	printf("1970년 1월 1일부터 현재까지의 초는 %d초 입니다\n", now);
	t = *localtime(&now);
	puts(asctime(&t));

	printf("현재의 연도: %d\n", t.tm_year + 1900);
	printf("현재의 월: %d\n", t.tm_mon + 1);
	printf("현재의 일: %d\n", t.tm_mday);
	printf("현재의 요일: %d\n", t.tm_wday);
}

// 실습과제 36.
void prob_36() {
	union year {
		int year;
		int grade;
	};

	typedef struct student {
		char name[20];
		char sex;
		int stid;
		union year y;
	} ST;

	ST st1 = { "kdhong", 'm', 1508001 };
	st1.y.grade = 4;
	printf("%s 학생은 %d학년입니다\n", st1.name, st1.y);
	printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.grade);
	printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.year);
	st1.y.year = 1;
	printf("%s 학생은 %d학년입니다\n", st1.name, st1.y);
	printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.grade);
	printf("%s 학생은 %d학년입니다\n", st1.name, st1.y.year);
}

// 실습과제 37.
void prob_37() {
	typedef enum week {SUN, MON, TUE, WED, THU, FRI, SAT} WEEK;
	typedef enum family_name {KIM = 100, LEE, PARK, JUNG, HONG } F_name;

	F_name fn = JUNG;
	printf("MAX=%d, MIN=%d\n", MAX, MIN);
	printf("SUN=%d\n", SUN);
	printf("MON=%d\n", MON);
	printf("TUE=%d\n", TUE);
	printf("WED=%d\n", WED);
	printf("THU=%d\n", THU);
	printf("FRI=%d\n", FRI);
	printf("SAT=%d\n", SAT);

	printf("JUNG은 %d번째 입니다\n", fn);

}
