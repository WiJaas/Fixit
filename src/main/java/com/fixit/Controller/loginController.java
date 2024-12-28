package com.fixit.Controller;


import com.fixit.Main;
import com.fixit.Model.UserDAO;
import com.fixit.Model.User;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class loginController {

    @FXML
    private Button login;
    @FXML
    private Label wronglogin;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;

    public loginController() {
        try {
            this.userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();

    }

    private void checkLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            // Create an instance of UserDAO to access the getAll method
            // Retrieve all users from the database
            List<User> users = userDAO.getAll();

            // Reset the error message to default before checking
            wronglogin.setText(""); // Reset previous error message

            // Iterate over the list of users to check for a match
            for (User user : users) {
                System.out.println(user); // For debugging, prints user details

                // Check if the username and password match
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    // User found and credentials match
                    wronglogin.setText("Success!");
                    // Optionally, you can navigate to another scene or perform other actions
                    return;  // Break out of the loop once a match is found
                }
            }

            // No match found
            wronglogin.setText("Failed!");  // Show failed login message if no match was found

        } catch (SQLException e) {
            e.printStackTrace();
            wronglogin.setText("Error occurred!"); // Handle database error
        }
    }


    public void userRegister(ActionEvent event) throws IOException {
        Main m = new Main();
//        m.changeScene("Register.fxml");


    }
}