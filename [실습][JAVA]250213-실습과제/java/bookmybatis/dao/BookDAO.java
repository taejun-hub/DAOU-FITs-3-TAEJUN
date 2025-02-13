package bookmybatis.dao;

import bookmybatis.vo.BookVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;

// SqlSessionFactory 를 DAO 에게 전달
public class BookDAO {
    private SqlSession sqlSession;

    public BookDAO() {}


    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<BookVO> selectObservableList(String text) throws Exception {
        return this.sqlSession.selectList("bookmybatis.myBook.selectObservableList", text);
    }

    public void registerBook(BookVO bookVO) throws Exception {
        this.sqlSession.insert("bookmybatis.myBook.registerBook", bookVO);
    }

    public void updateBook(BookVO book) throws Exception {
        this.sqlSession.update("bookmybatis.myBook.updateBook", book);

    }

    public void deleteBook(String bisbn) throws Exception {
        this.sqlSession.delete("bookmybatis.myBook.deleteBook", bisbn);
    }
}
