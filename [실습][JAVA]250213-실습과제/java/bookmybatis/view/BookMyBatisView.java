package bookmybatis.view;

import bookmybatis.controller.BookMyBatisController;
import bookmybatis.dao.BookDAO;
import bookmybatis.service.BookMyBatisService;

import example.mybatis.MyBatisSessionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSessionFactory;

public class BookMyBatisView extends Application {
        @Override
        public void start(Stage stage) {

            Parent root = null;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bookmybatis.fxml"));
            SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
            BookDAO bookDAO = new BookDAO();
            BookMyBatisService bookMyBatisService = new BookMyBatisService(sqlSessionFactory, bookDAO);
            try {
                fxmlLoader.setControllerFactory(param -> new BookMyBatisController(bookMyBatisService));
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

