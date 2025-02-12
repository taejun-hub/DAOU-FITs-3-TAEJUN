package bookdi.dao;

import bookdi.db.ConnectionMaker;
import bookdi.vo.BookVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO {
    Connection conn;

    public BookDAO(Connection conn) {
        this.conn = conn;
    }

    public ObservableList<BookVO> select(String text) {
        ObservableList<BookVO> books = FXCollections.observableArrayList();

        try {
            String sql = "select bisbn, btitle, bprice, bauthor ";
            sql += "from book where btitle like ?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, "%" + text + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookVO book = new BookVO(rs.getString("bisbn"),
                        rs.getString("btitle"),
                        rs.getInt("bprice"),
                        rs.getString("bauthor"));
                books.add(book);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public void delete(String isbn) {
        String sql = "delete from book where bisbn=?";
        try {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
            System.out.println("Book deleted to " + isbn);
        } catch (SQLException e) {
            System.out.println("Cannot delete book");
        }
    }

    public void update(BookVO book) {
        String sql = "update book set btitle=?, bprice=?, bauthor=? where bisbn=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getBtitle());
            pstmt.setInt(2, book.getBprice());
            pstmt.setString(3, book.getBauthor());
            pstmt.setString(4, book.getBisbn());
            pstmt.executeUpdate();
            System.out.println("Book updated to " + book.getBisbn() + " " + book.getBtitle());

        }catch (SQLException e) {
            System.out.println("Cannot update book");
        }
    }
}
