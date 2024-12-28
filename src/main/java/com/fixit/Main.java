package com.fixit;

import com.fixit.Model.User;
import com.fixit.Model.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View/.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // Adjusted size to fit the form
        stage.setTitle("Fixit App - User Registration");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}