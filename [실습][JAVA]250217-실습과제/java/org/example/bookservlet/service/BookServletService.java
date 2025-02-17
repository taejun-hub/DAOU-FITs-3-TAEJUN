package org.example.bookservlet.service;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bookservlet.dao.BookDAO;
import org.example.bookservlet.vo.BookSearchVO;
import org.example.bookservlet.vo.BookVO;

import java.util.List;

public class BookServletService {
    private SqlSessionFactory sqlSessionFactory;
    private BookDAO bookDAO;
//
    public BookServletService(SqlSessionFactory sqlSessionFactory, BookDAO bookDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.bookDAO = bookDAO;
    }

    public List<BookVO> searchBookByKeywordAndPrice(String keyword, int price) {
        List<BookVO> bookVOList = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.bookDAO.setSqlSession(sqlSession);
        try {
            BookSearchVO bookSearchVO = new BookSearchVO(keyword, price);
            bookVOList = this.bookDAO.selectList(bookSearchVO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
            System.out.println("검색 성공");
        }
        return bookVOList;
    };

}
