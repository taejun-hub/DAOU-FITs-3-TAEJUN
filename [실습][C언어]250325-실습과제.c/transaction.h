#pragma once
#define MAX_NAME_LEN 32
#define MAX_TYPE_LEN 8
#define CSV_FILE_NAME "stocks.csv"
#define BIN_FILE_NAME "stocks.dat"

typedef struct {
    int id;
    char user_name[MAX_NAME_LEN];
    char stock_name[MAX_NAME_LEN];
    char type[MAX_TYPE_LEN];
    int quantity;
    double price;
} Transaction;


int get_transactions_count();
void get_transactions(Transaction* t_arr);
void print_transactions();
void save_transactions();
void get_transaction_by_user_name();
void add_transaction();
void update_transaction();
void delete_transaction();