package org.example.bookservlet.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.bookservlet.vo.BookSearchVO;
import org.example.bookservlet.vo.BookVO;

import java.util.List;

// SqlSessionFactory 를 DAO 에게 전달
public class BookDAO {
    private SqlSession sqlSession;

    public BookDAO() {}


    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<BookVO> selectList(BookSearchVO bookSearchVO) throws Exception {
        return this.sqlSession.selectList("bookmybatis.myBook.selectList", bookSearchVO);
    }

}
