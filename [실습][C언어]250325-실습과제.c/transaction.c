#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "db.h"
#include "transaction.h"



int get_transactions_count() {
    OCIStmt* stmthp;
    sword status;
    OCIDefine* def1 = NULL;
    int count = -1;
    char* sql = "SELECT COUNT(ID) FROM TRANSACTIONS";

    // SQL문 핸들을 생성
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);

    // 2. 쿼리 실행
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    // 3. 데이터 바인딩 (결과를 받을 변수)
    OCIDefineByPos(stmthp, &def1, errhp, 1, &count, sizeof(count), SQLT_INT, NULL, NULL,
        NULL, OCI_DEFAULT);

    // 4. 데이터 가져오기 
    if ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT))
        == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {
        printf("데이터 개수 조회 결과: %d\n", count);
    }

    OCIHandleFree(stmthp, OCI_HTYPE_STMT);

    return count;
}


void get_transactions(Transaction* t_arr) {
    OCIStmt* stmthp;
    sword status;
    OCIDefine* def1 = NULL, * def2 = NULL, * def3 = NULL, * def4 = NULL, * def5 = NULL, * def6 = NULL;
    int id;
    char user_name[MAX_NAME_LEN];
    char stock_name[MAX_NAME_LEN];
    char type[MAX_TYPE_LEN];
    int quantity;
    double price;
    char* sql = "SELECT * FROM TRANSACTIONS ORDER BY ID";

    // SQL문 핸들을 생성
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);
    //free(sql);
    // 2. 쿼리 실행
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    // 3. 데이터 바인딩 (결과를 받을 변수)
    OCIDefineByPos(stmthp, &def1, errhp, 1, &id, sizeof(id), SQLT_INT, NULL, NULL,
        NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def2, errhp, 2, stock_name, sizeof(stock_name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def3, errhp, 3, type, sizeof(type), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def4, errhp, 4, &quantity, sizeof(quantity), SQLT_INT, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def5, errhp, 5, &price, sizeof(price), SQLT_BDOUBLE, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def6, errhp, 6, user_name, sizeof(user_name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);


    // 4. 데이터 가져오기 
    int idx = 0;
    while ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT))
        == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {
        //printf("ID: %d, 고객: %s, 종목: %s, 유형: %s, 수량: %d, 가격 %.2lf\n", id, user_name, stock_name, type, quantity, price); //<-- 여기 코드 추가  조회 후 출력하기
        t_arr[idx].id = id;
        strcpy(t_arr[idx].user_name, user_name);
        strcpy(t_arr[idx].stock_name, stock_name);
        strcpy(t_arr[idx].type, type);
        t_arr[idx].quantity = quantity;
        t_arr[idx].price = price;
        idx++;
    }

    OCIHandleFree(stmthp, OCI_HTYPE_STMT);
}


void print_transactions() {
    int count = get_transactions_count();
    Transaction* t_arr = (Transaction*)malloc(sizeof(Transaction) * count);
    if (t_arr == NULL)
    {
        printf("malloc 실패\n");
        return;
    }
    get_transactions(t_arr);

    printf("테이블 조회 결과:\n");
    printf("-----------------------------------------------------------------------\n");
    for (int i = 0; i < count; i++)
    {
        printf("ID: %d, 고객: %s, 종목: %s, 유형: %s, 수량: %d, 가격 %.2lf\n",
            t_arr[i].id, t_arr[i].user_name, t_arr[i].stock_name, t_arr[i].type, t_arr[i].quantity, t_arr[i].price);
    }
    printf("-----------------------------------------------------------------------\n");

    free(t_arr);
}

void save_transactions() {
    int count = get_transactions_count();
    Transaction* t_arr = (Transaction*)malloc(sizeof(Transaction) * count);
    if (t_arr == NULL)
    {
        printf("malloc 실패\n");
        return;
    }
    get_transactions(t_arr);

    FILE* csv = fopen(CSV_FILE_NAME, "w");
    FILE* bin = fopen(BIN_FILE_NAME, "wb");

    if (csv == NULL || bin == NULL)
    {
        printf("파일 열기 실패");
        return;
    }
    // CSV 헤더 쓰기
    fprintf(csv, "고객ID,종목코드,거래유형,수량,체결가\n");
    for (int i = 0; i < count; i++)
    {
        fprintf(csv, "%d,%s,%s,%s,%d,%.2lf\n",
            t_arr[i].id, t_arr[i].user_name, t_arr[i].stock_name, t_arr[i].type, t_arr[i].quantity, t_arr[i].price);
        fwrite(&t_arr[i], sizeof(Transaction), 1, bin);
    }

    free(t_arr);
    fclose(bin);
    fclose(csv);

    printf("csv 파일로 내보내기가 완료되었습니다 ! (%s)\n", CSV_FILE_NAME);
    printf("dat 파일로 내보내기가 완료되었습니다 ! (%s)\n", BIN_FILE_NAME);
}



void get_transaction_by_user_name() {
    OCIStmt* stmthp;
    sword status;
    OCIBind* bnd1 = NULL;
    OCIDefine* def1 = NULL, * def2 = NULL, * def3 = NULL, * def4 = NULL, * def5 = NULL, * def6 = NULL;
    int id;
    char user_name[MAX_NAME_LEN];
    char stock_name[MAX_NAME_LEN];
    char type[MAX_TYPE_LEN];
    int quantity;
    double price;
    
    char target_name[MAX_NAME_LEN];
    printf("검색할 고객 이름: ");
    scanf("%s", target_name);
    char* sql = "SELECT * FROM transactions WHERE user_name = :1  ORDER BY ID";


    // SQL문 핸들을 생성
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql), OCI_NTV_SYNTAX,
        OCI_DEFAULT);
    //free(sql);

    OCIBindByPos(stmthp, &bnd1, errhp, 1, target_name, sizeof(target_name), SQLT_STR,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    // 2. 쿼리 실행
    OCIStmtExecute(svchp, stmthp, errhp, 0, 0, NULL, NULL, OCI_DEFAULT);

    // 3. 데이터 바인딩 (결과를 받을 변수)
    OCIDefineByPos(stmthp, &def1, errhp, 1, &id, sizeof(id), SQLT_INT, NULL, NULL,
        NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def2, errhp, 2, stock_name, sizeof(stock_name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def3, errhp, 3, type, sizeof(type), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def4, errhp, 4, &quantity, sizeof(quantity), SQLT_INT, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def5, errhp, 5, &price, sizeof(price), SQLT_BDOUBLE, NULL,
        NULL, NULL, OCI_DEFAULT);
    OCIDefineByPos(stmthp, &def6, errhp, 6, user_name, sizeof(user_name), SQLT_STR, NULL,
        NULL, NULL, OCI_DEFAULT);

    printf("테이블 검색 결과:\n");
    printf("-----------------------------------------------------------------------\n");
    //printf("|  ID  |        NAME        |    PHONE_NUMBER    |        EMAIL        |\n");
    //printf("-----------------------------------------------------------------------\n");
    // 4. 데이터 가져오기 
    while ((status = OCIStmtFetch2(stmthp, errhp, 1, OCI_DEFAULT, 0, OCI_DEFAULT))
        == OCI_SUCCESS || status == OCI_SUCCESS_WITH_INFO) {
        printf("ID: %d, 고객: %s, 종목: %s, 유형: %s, 수량: %d, 가격 %.2lf\n", id, user_name, stock_name, type, quantity, price); //<-- 여기 코드 추가  조회 후 출력하기 
    }
    printf("-----------------------------------------------------------------------\n");

    OCIHandleFree(stmthp, OCI_HTYPE_STMT);
}

void add_transaction() {

    OCIStmt* stmthp;
    OCIBind* bnd1 = NULL, * bnd2 = NULL, * bnd3 = NULL, * bnd4 = NULL, * bnd5 = NULL;
    char* sql = "INSERT INTO transactions (user_name, stock_name, type, quantity, price) VALUES (:1, :2, :3, :4, :5)";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql), OCI_NTV_SYNTAX, OCI_DEFAULT);

    //int customer_id = 1;
    char user_name[MAX_NAME_LEN];
    char stock_name[MAX_NAME_LEN];
    char type[MAX_TYPE_LEN];
    int quantity;
    double price;

    printf("고객 이름: ");
    scanf("%s", user_name);
    printf("주식 종목명: ");
    scanf("%s", stock_name);
    printf("거래 유형: ");
    scanf("%s", type);
    printf("거래 수량: ");
    scanf("%d", &quantity);
    printf("거래 가격: ");
    scanf("%lf", &price);

    OCIBindByPos(stmthp, &bnd1, errhp, 1, user_name, sizeof(user_name), SQLT_STR,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd2, errhp, 2, stock_name, sizeof(stock_name), SQLT_STR,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd3, errhp, 3, type, sizeof(type), SQLT_STR,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd4, errhp, 4, &quantity, sizeof(quantity), SQLT_INT,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd5, errhp, 5, &price, sizeof(price), SQLT_BDOUBLE,
        NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    if (OCIStmtExecute(svchp, stmthp, errhp, 1, 0, NULL, NULL,
        OCI_COMMIT_ON_SUCCESS) != OCI_SUCCESS) {
        check_error(errhp);
    }
    else {
        printf("데이터 삽입 완료!\n");
    }
}

void update_transaction() {
    OCIStmt* stmthp;
    OCIBind* bnd1 = NULL, * bnd2 = NULL, * bnd3 = NULL, *bnd4 = NULL;
    char* sql = "UPDATE transactions SET quantity = :1, price = :2 WHERE id = :3";
    OCIHandleAlloc(envhp, (void**)&stmthp, OCI_HTYPE_STMT, 0, NULL);
    OCIStmtPrepare(stmthp, errhp, (text*)sql, strlen(sql),
        OCI_NTV_SYNTAX, OCI_DEFAULT);

    int id;
    int quantity;
    double price;

    printf("수정 할 거래 ID: ");
    scanf("%d", &id);
    printf("새 수량: ");
    scanf("%d", &quantity);
    printf("새 가격: ");
    scanf("%lf", &price);
    //printf("%d, %d, %lf\n", id, quantity, price);
    // 바인딩 변수 설정 (UPDATE)
    OCIBindByPos(stmthp, &bnd1, errhp, 1, &quantity, sizeof(quantity),
        SQLT_INT, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd2, errhp, 2, &price, sizeof(price),
        SQLT_BDOUBLE, NULL, NULL, NULL, 0, NULL, OCI_DEFAULT);
    OCIBindByPos(stmthp, &bnd3, errhp, 3, &id, sizeof(id), SQLT_INT,
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


void delete_transaction() {
    OCIStmt* stmthp;
    OCIBind* bnd1 = NULL;
    int id;  // 삭제할 ID 값 (예: 2번 ID)
    char* sql = "DELETE FROM transactions WHERE id = :1";  // ID를 기준으로 삭제
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
