#pragma once
#include <oci.h>
int db_init();
void db_cleanup();
extern OCIEnv* envhp;
extern OCIError* errhp;
extern OCISvcCtx* svchp;