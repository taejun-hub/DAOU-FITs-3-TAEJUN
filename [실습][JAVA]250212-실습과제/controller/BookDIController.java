package bookdi.controller;

import bookdi.service.BookDIService;
import bookdi.vo.BookVO;
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

public class BookDIController implements Initializable {

    // 이벤트 처리를 위해서는 View에 있는 control 들에 대한 reference 가 필요
    @FXML private TableView<BookVO> tableView;
    @FXML private TableColumn<BookVO, String> isbnCol;
    @FXML private TableColumn<BookVO, String> titleCol;
    @FXML private TableColumn<BookVO, Integer> priceCol;
    @FXML private TableColumn<BookVO, String> authorCol;
    @FXML private TextField textField;
    @FXML private Button searchBtn;
    @FXML private Button deleteBtn;

    private BookDIService bookDIService;
    private ObservableList<BookVO> books;

    public BookDIController(BookDIService bookDIService) {
        this.bookDIService = bookDIService;
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
            this.bookDIService.updateBook(book);
        });

        tableView.setEditable(true);

        searchBtn.setOnAction(event -> {
            // 검색 버튼이 클릭되면 키워들를 이용한 검색 작업 진행
            // 로직 처리를 위해선 servie 객체가 필요
            books = this.bookDIService.searchBookByKeyword(textField.getText());
            tableView.setItems(books);
        });

        deleteBtn.setOnAction(event -> {
            BookVO book = tableView.getSelectionModel().getSelectedItem();
            if (book != null) {
                books.remove(book);
                this.bookDIService.deleteBook(book.getBisbn());
            }
        });

        System.out.println("BookSearchMVCController initialized");
    }
}
