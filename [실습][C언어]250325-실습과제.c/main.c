#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include "db.h"
#include "transaction.h"


int main()
{
    if (db_init() != 0) {
        printf("DB 연결 실패\n");
        return 1;
    }

    int choice;
    int exit_flag = 0;
    while (1)
    {
        if (exit_flag) break;
        printf("=====주식 거래 시스템=====\n");
        printf("1. 거래 추가\n");
        printf("2. 모든 거래 조회\n");
        printf("3. 고객 거래 검색\n");
        printf("4. 거래 수정\n");
        printf("5. 거래 삭제\n");
        printf("6. 종료\n");
        printf("선택: ");
        scanf("%d", &choice);
        switch (choice)
        {
        case 1:
            add_transaction();
            break;
        case 2:
            print_transactions();
            break;
        case 3:
            get_transaction_by_user_name();
            break;
        case 4:
            update_transaction();
            break;
        case 5:
            delete_transaction();
            break;
        case 6:
            save_transactions();
            exit_flag = 1;
            break;
        default:
            printf("잘못된 선택입니다\n");
            break;
        }
    }

    db_cleanup();
    return 0;
}

