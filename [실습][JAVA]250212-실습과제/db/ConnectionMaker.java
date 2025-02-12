package bookdi.db;

import java.sql.Connection;

public interface ConnectionMaker {
    public Connection getConnection();
}
