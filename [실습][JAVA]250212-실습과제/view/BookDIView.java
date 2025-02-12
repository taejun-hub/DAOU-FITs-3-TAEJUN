package bookdi.view;

import bookdi.controller.BookDIController;
import bookdi.dao.BookDAO;
import bookdi.db.ConnectionMaker;
import bookdi.db.KConnectionMaker;
import bookdi.service.BookDIService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class BookDIView extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // 화면 구성
        // Stage - Scene - Parent(LayoutManager)
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bookdi.fxml"));
        ConnectionMaker connectionMaker = new KConnectionMaker();
        Connection conn = connectionMaker.getConnection();
        BookDAO bookDAO = new BookDAO(conn);
        BookDIService bookDIService = new BookDIService(bookDAO, conn);
        try {
            fxmlLoader.setControllerFactory(param -> new BookDIController(bookDIService));
            root = fxmlLoader.load();

        } catch (Exception e) {
            stage.setTitle("FXMLoad fail");
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("도서 검색, 수정, 삭제 DI");

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}
