package com.fixit;

import com.fixit.Model.User;
import com.fixit.Model.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    private static Stage stage;  // Renaming stg to stage for better readability

    public static void main(String[] args) {

        launch(args);  // Launch the JavaFX application

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;  // Assign primaryStage to the static stage variable
        stage.setResizable(false);  // Prevent resizing of the window

        // Load the initial scene (login.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("FixIt");  // Set the window title
        stage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(true); // Allow resizing// Set the scene and dimensions
        stage.show();  // Display the window
    }

    // Method to change the current scene
    public static void changeScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlFile));
        stage.setScene(new Scene(root));
    }
}
