#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <oci.h>
#include "db.h"


void check_error(OCIError* errhp) {
    text errbuf[512];
    sb4 errcode = 0;
    OCIErrorGet((dvoid*)errhp, (ub4)1, (text*)NULL, &errcode, errbuf, (ub4)sizeof(errbuf),
        OCI_HTYPE_ERROR);
    printf("Error: %s\n", errbuf);
}

void get_customers() {
    OCIStmt* stmthp;
    sword status; // signed int 형 
    OCIDefine* def1 = NULL;
    OCIDefine* def2 = NULL;
    OCIDefine* def3 = NULL;
    OCIDefine* def4 = NULL;
    int id;
    char name[100];
    char phone_number[50];
    char email[100];
    char* sql = "SELECT customer_id, name, phone_number, email FROM CUSTOMERS";

    // SQL문 핸들을 생성
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);
    // 2. 쿼리 실행
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    // 3. 데이터 바인딩 (결과를 받을 변수)
    OCIDefineByPos(stmthp, &def1, errhp, 1, &id, sizeof(id), SQLT_INT, NULL, NULL,
        NULL, OCI_DEFAULT); 
    OCIDefineByPos(stmthp, &def2, errhp, 2, name, sizeof(name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def3, errhp, 3, phone_number, sizeof(phone_number), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def4, errhp, 4, email, sizeof(email), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);

    printf("테이블 조회 결과:\n"); 
    printf("-----------------------------------------------------------------------\n");
    printf("|  ID  |        NAME        |    PHONE_NUMBER    |        EMAIL        |\n");
    printf("-----------------------------------------------------------------------\n");
    // 4. 데이터 가져오기 <---여기 코드 추가
    while ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT))
        == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {
        printf("| %4d | %-18s | %-18s | %-19s |\n", id, name, phone_number, email); //<-- 여기 코드 추가  조회 후 출력하기 
    }
    printf("-----------------------------------------------------------------------\n");


    OCIHandleFree(stmthp, OCI_HTYPE_STMT);
}

void add_customer() {

    OCIStmt* stmthp;
    OCIBind *bnd1 = NULL, *bnd2 = NULL, *bnd3 = NULL, *bnd4 = NULL;
    char* sql = "INSERT INTO customers (customer_id, name, phone_number, email) VALUES (:1, :2, :3, :4)";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    int customer_id = 1;
    char name[100];
    char phone_number[50];
    char email[100];

    printf("고객 ID 입력: ");
    scanf("%d", &customer_id);
    printf("고객 이름 입력: ");
    scanf("%s", &name);
    printf("고객 전화번호 입력: ");
    scanf("%s", &phone_number);
    printf("고객 이메일 입력: ");
    scanf("%s", &email);

    OCIBindByPos(stmthp, &bnd1, errhp, 1, &customer_id, sizeof(customer_id), SQLT_INT,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd2, errhp, 2, name, sizeof(name), SQLT_STR,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd3, errhp, 3, phone_number, sizeof(phone_number), SQLT_STR,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd4, errhp, 4, email, sizeof(email), SQLT_STR,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    if (OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL,
        OCI_COMMIT_ON_SUCCESS) != OCI_SUCCESS) {
        check_error(errhp);
    }
    else {
        printf("데이터 삽입 완료!\n");
    }
}

void update_customer() {
    OCIStmt* stmthp;
    OCIBind* bnd1 = NULL, * bnd2 = NULL, * bnd3 = NULL, * bnd4 = NULL;
    char* sql = "UPDATE customers SET NAME = :1 WHERE customer_id = :2";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql),
        OCI_NTV_SYNTAX, OCI_DEFAULT);

    int id;  // 수정할 ID
    char name[50]; // 수정할 이름
    printf("수정 할 고객 ID: \n");
    scanf("%d", &id);
    printf("수정 할 이름: \n");
    scanf("%s", &name);

    // 바인딩 변수 설정 (UPDATE)
    OCIBindByPos(stmthp, &bnd1, errhp, 1, name, sizeof(name),
        SQLT_STR, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd2, errhp, 2, &id, sizeof(id), SQLT_INT,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);

    // SQL 실행
    if (OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL,
        OCI_COMMIT_ON_SUCCESS) != OCI_SUCCESS) {
        check_error(errhp);
    }
    else {
        printf("데이터 수정 완료!\n");
    }
}


void delete_customer() {
    OCIStmt* stmthp;
    OCIBind* bnd1 = NULL;
    int id;  // 삭제할 ID 값 (예: 2번 ID)
    char* sql = "DELETE FROM customers WHERE customer_id = :1";  // ID를 기준으로 삭제
    printf("삭제할 ID: ");
    scanf("%d", &id);

    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);

    // 바인딩 변수 설정 (DELETE)
    OCIBindByPos(stmthp, &bnd1, errhp, 1, &id, sizeof(id), SQLT_INT,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    // SQL 실행
    if (OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL,
        OCI_COMMIT_ON_SUCCESS) != OCI_SUCCESS) {
        check_error(errhp);
    }
    else {
        printf("데이터 삭제 완료!\n");
    }
}


int main() {
    if (db_init() != 0) {
        printf("DB 연결 실패\n");
        return 1;
    }
    int choice;
    while (1)
    {
        printf("고객 정보 테이블 CRUD\n");
        printf("1. 고객 조회\n");
        printf("2. 고객 생성\n");
        printf("3. 고객 수정\n");
        printf("4. 고객 삭제\n");
        printf("5. 종료\n");
        printf("선택: ");
        scanf("%d", &choice);
        switch (choice)
        {
        case 1:
            get_customers();
            break;
        case 2:
            add_customer();
            break;
        case 3:
            update_customer();
            break;
        case 4:
            delete_customer();
            break;
        case 5:
            return;
        default:
            printf("잘못된 선택입니다\n");
            break;
        }
    }

    db_cleanup();
    return 0;
}