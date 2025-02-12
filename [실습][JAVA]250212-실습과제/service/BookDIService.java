package bookdi.service;

import bookdi.dao.BookDAO;
import bookdi.db.ConnectionMaker;
import bookdi.vo.BookVO;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class BookDIService {
    private BookDAO bookDAO;
    Connection conn;

    public BookDIService(BookDAO bookDAO, Connection conn) {
        this.bookDAO = bookDAO;
        this.conn = conn;
    }

    public ObservableList<BookVO> searchBookByKeyword(String text) {
        return this.bookDAO.select(text);
    };

    public void updateBook(BookVO book) {
        this.bookDAO.update(book);
    }

    public void deleteBook(String isbn) {
        this.bookDAO.delete(isbn);
    }
}
