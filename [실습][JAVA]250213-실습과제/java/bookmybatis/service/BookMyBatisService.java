package bookmybatis.service;

import bookmybatis.dao.BookDAO;
import bookmybatis.vo.BookVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BookMyBatisService {
    private SqlSessionFactory sqlSessionFactory;
    private BookDAO bookDAO;

    public BookMyBatisService(SqlSessionFactory sqlSessionFactory, BookDAO bookDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.bookDAO = bookDAO;

    }

    public ObservableList<BookVO> searchBookByKeyword(String text) {
        ObservableList<BookVO> books = FXCollections.observableArrayList();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.bookDAO.setSqlSession(sqlSession);
        try {
             List<BookVO> list  = this.bookDAO.selectObservableList(text);
             for (BookVO book : list) {
                 books.add(book);
             }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
            System.out.println("조회 성공");
        }
        return books;
    };

    public void  registerBook(BookVO bookVO) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.bookDAO.setSqlSession(sqlSession);
        try {
            this.bookDAO.registerBook(bookVO);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
            System.out.println("등록 성공");
        }

    }

    public void updateBook(BookVO book) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.bookDAO.setSqlSession(sqlSession);
        try {
            this.bookDAO.updateBook(book);
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
            System.out.println("수정 성공");
        }
    }

    public void deleteBook(String isbn) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.bookDAO.setSqlSession(sqlSession);
        try {
            this.bookDAO.deleteBook(isbn);
            sqlSession.commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            sqlSession.close();
            System.out.println("삭제 성공");
        }
    }
}
