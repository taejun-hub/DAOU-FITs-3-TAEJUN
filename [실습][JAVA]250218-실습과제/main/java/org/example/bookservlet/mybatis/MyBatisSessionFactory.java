package org.example.bookservlet.mybatis;



import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

// 이 Java Class 가 하는 일은 DAO에서 필요한 SqlSessionFactory 라는 객체를 생성한다
// SqlSessionFactory 를 통해 SqlSession 객체를 생성하고 이를 통해 sql 문을 실행할 수 있다.
public class MyBatisSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "./SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            System.out.println("reader: " + reader);

            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                // environment 파라미터가 없으면, default 환경이 로드

                System.out.println("SqlSessionFactory created");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
