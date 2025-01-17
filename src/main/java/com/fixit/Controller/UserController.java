package com.fixit.Controller;
import com.fixit.Model.User;
import com.fixit.Model.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class UserController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField firstNameField;  // Add field for first name

    @FXML
    private TextField lastNameField;
    @FXML
    private TableView<User> mytab;// Add field for last name

    private UserDAO userDAO;


    // Initialize the controller and UserDAO
    public UserController() {
        try {
            this.userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method is triggered when the "Save User" button is clicked
    @FXML
    private void handleSaveUser() {
        // Retrieve values from the UI fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();
        String department = departmentField.getText();
        String firstName = firstNameField.getText();  // Get first name
        String lastName = lastNameField.getText();    // Get last name

        // Validation: Check if fields are filled
        if (username.isEmpty() || password.isEmpty() || role == null || department.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showAlert("Error", "All fields must be filled", AlertType.ERROR);
            return;
        }

        // Create a new user object with the form data
        User newUser = new User(0, username, password, role, firstName, lastName, department);

        // Save the user to the database
        try {
            userDAO.save(newUser);
            showAlert("Success", "User saved successfully!", AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error", "Failed to save user: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }



    // Helper method to show alerts to the user
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }





}
