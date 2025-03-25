#pragma once

#include <oci.h>
int db_init();
void db_cleanup();
void check_error(OCIError* errhp);
char* read_sql_file(const char* filename);
extern OCIEnv* envhp;
extern OCIError* errhp;
extern OCISvcCtx* svchp;