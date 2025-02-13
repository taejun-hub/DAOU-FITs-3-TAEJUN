package bookmybatis.controller;

import bookmybatis.service.BookMyBatisService;
import bookmybatis.vo.BookVO;
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
import java.util.ResourceBundle;

public class BookMyBatisController implements Initializable {
    // 이벤트 처리를 위해서는 View에 있는 control 들에 대한 reference 가 필요
    @FXML
    private TableView<BookVO> tableView;
    @FXML private TableColumn<BookVO, String> isbnCol;
    @FXML private TableColumn<BookVO, String> titleCol;
    @FXML private TableColumn<BookVO, Integer> priceCol;
    @FXML private TableColumn<BookVO, String> authorCol;
    @FXML private TextField textField;
    @FXML private TextField isbnTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField authorTextField;
    @FXML private Button searchBtn;
    @FXML private Button deleteBtn;
    @FXML private Button registerBtn;

    private BookMyBatisService bookMyBatisService;
    private ObservableList<BookVO> books;

    public BookMyBatisController(BookMyBatisService bookMyBatisService) {
        this.bookMyBatisService = bookMyBatisService;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // View의 control(버튼같은..)의 대한 이벤트를 등록
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("btitle"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("bprice"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("bauthor"));

        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        titleCol.setOnEditCommit(event -> {
            BookVO book = event.getRowValue();
            book.setBtitle(event.getNewValue());
            this.bookMyBatisService.updateBook(book);
        });

        tableView.setEditable(true);

        searchBtn.setOnAction(event -> {
            books = this.bookMyBatisService.searchBookByKeyword(textField.getText());
            tableView.setItems(books);
        });

        registerBtn.setOnAction(event -> {
            String isbn = isbnTextField.getText();
            String title = titleTextField.getText();
            int price = Integer.parseInt(priceTextField.getText());
            String author = authorTextField.getText();
            BookVO book = new BookVO(isbn, title, price, author);
            this.bookMyBatisService.registerBook(book);

        });

        deleteBtn.setOnAction(event -> {
            BookVO book = tableView.getSelectionModel().getSelectedItem();
            if (book != null) {
                books.remove(book);
                this.bookMyBatisService.deleteBook(book.getBisbn());
            }
        });

        System.out.println("BookSearchMVCController initialized");
    }
}
