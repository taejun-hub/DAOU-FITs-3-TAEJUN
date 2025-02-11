package booksearch;

import booksearch.vo.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class JavaFXJDBCTableViewController implements Initializable {
    @FXML
    private TableView<Book> tableView;

    @FXML private TableColumn<Book, String> isbnCol;
    @FXML private TableColumn<Book, String> titleCol;
    @FXML private TableColumn<Book, Integer> priceCol;
    @FXML private TableColumn<Book, String> authorCol;
    @FXML private Button searchBtn;
    @FXML private Button deleteBtn;
    @FXML private TextField textField;


    private Connection conn;
    private PreparedStatement pstmt = null;
    ObservableList<Book> books = FXCollections.observableArrayList();

    public JavaFXJDBCTableViewController() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String id = "C##DEV";
            String pw = "1234";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("Connected to database" + conn);


        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load oracle driver");
        } catch (SQLException e) {
            System.out.println("Cannot connect to database");
        }

    }

    public void updateBook(Book book) {
        String sql = "update book set btitle=?, bprice=?, bauthor=? where bisbn=?";
        try {
            pstmt = conn.prepareStatement(sql);
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

    public void deleteBook(Book book) {
        String sql = "delete from book where bisbn=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getBisbn());
            pstmt.executeUpdate();
            System.out.println("Book deleted to " + book.getBisbn());
        } catch (SQLException e) {
            System.out.println("Cannot delete book");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("JavaFXJDBCTableViewController Initialize");

        isbnCol.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("btitle"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("bprice"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("bauthor"));


        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        titleCol.setOnEditCommit(event -> {
            Book book = event.getRowValue();
            book.setBtitle(event.getNewValue());
            updateBook(book);
        });


        authorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        authorCol.setOnEditCommit(event -> {
            Book book = event.getRowValue();
            book.setBtitle(event.getNewValue());
            updateBook(book);
        });

        tableView.setEditable(true);


        searchBtn.setOnAction(event -> {

            String sql = "select bisbn, btitle, bprice, bauthor ";
            sql += "from book where btitle like ?";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + textField.getText() + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Book book = new Book(rs.getString("bisbn"),
                            rs.getString("btitle"),
                            rs.getInt("bprice"),
                            rs.getString("bauthor"));
                    books.add(book);
                }

            } catch (SQLException e) {
                System.out.println("SQL Exception");
            }

            tableView.setItems(books);
        });

        deleteBtn.setOnAction(event -> {
            Book book = tableView.getSelectionModel().getSelectedItem();
            if (book != null) {
                books.remove(book);
                deleteBook(book);
            }
        });

    }
}
