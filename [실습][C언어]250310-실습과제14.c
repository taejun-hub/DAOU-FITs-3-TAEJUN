#include <stdio.h>
#include "prob.h"
#define N 5
#define SIZE 5
int salary;
int compute_toatal_salary();
int compute_tax(int amount);
void display_tax_returns(int total_salarty, int tax_target, int tax);

void prob_14() {
	int exemption = 10000000;
	int total_salary, tax_target, tax;

	printf("\n월 급여는? ");
	scanf_s("%d", &salary);

	total_salary = compute_toatal_salary();
	tax_target = total_salary - exemption;
	tax = compute_tax(tax_target);
	display_tax_returns(total_salary, tax_target, tax);
	
}

int compute_toatal_salary() {
	int bonus_rate = 300;
	return (salary * 12) + (int)(salary * bonus_rate * 0.01);
}

int compute_tax(int amount) {
	int tax_rate;

	if (salary >= 5000000)
	{
		tax_rate = 5;
	}
	else if (salary >= 2500000) { tax_rate = 3; }
	else {
		tax_rate = 2;
	}
	return (int)(amount * tax_rate * 0.01);
}

void display_tax_returns(int total_salarty, int tax_target, int tax) {
	printf("\n >>> 세금 내역서 <<<");
	printf("\n-----------------------");
	printf("\n연 급여 : %8d원", total_salarty);
	printf("\n세금 부과 대상액 : %8d원", tax_target);
	printf("\n최종 세금 부과액 : %8d원", tax);
}
