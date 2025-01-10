package com.fixit.Controller;

import com.fixit.Main;
import com.fixit.Model.User;
import com.fixit.Model.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

import static com.fixit.Controller.AuthController.userLogOut;

public class AdminController {
    public AnchorPane mainContentArea;
    public Button logoutButton;
    public GridPane userCreationForm;
    public ScrollPane scrollPane;
    public TextField firstName;
    public TextField lastName;
    public TextField department;
    public TextField role;
    public Button ok;
    public Button delete;
    public Button home;
    @FXML
    private TextField userIdField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<User> mytab;
    @FXML
    private TableColumn<User, Integer> col_id;
    @FXML
    private TableColumn<User, String> col_username;
    @FXML
    private TableColumn<User, String> col_password;
    @FXML
    private TableColumn<User, String> col_role;
    @FXML
    private TableColumn<User, String> col_department;
    @FXML
    private TableColumn<User, String> col_firstName;
    @FXML
    private TableColumn<User, String> col_lastName;

    private UserDAO userDAO;


    public AdminController() {
        try {
            this.userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to load the user creation form
    @FXML
    private void Click_CreateUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fixit/userCreation_page.fxml"));
            Node userCreationView = loader.load();

            // Check if mainContentArea is null
            if (mainContentArea == null) {
                System.out.println("mainContentArea is null. Check FXML fx:id and controller setup.");
                return;
            }

            // Clear the current content and set the new view
            mainContentArea.getChildren().clear();
            mainContentArea.getChildren().add(userCreationView);



            // Set anchors to center the view
            AnchorPane.setTopAnchor(userCreationView, 0.0);
            AnchorPane.setBottomAnchor(userCreationView, 0.0);
            AnchorPane.setLeftAnchor(userCreationView, 0.0);
            AnchorPane.setRightAnchor(userCreationView, 0.0);

            // Change the "Logout" button to become "Home"
            if (logoutButton != null) {
                logoutButton.setText("Home");
                logoutButton.setOnAction(event -> navigateToHome());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the view users table
    @FXML
    private void Click_ViewUser() {
        loadUI("viewUser_page.fxml");
    }

    // Method to load the report view
    @FXML
    private void Click_Report() {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fixit/report_page.fxml"));
        Node reportView = loader.load();

        // Check if mainContentArea is null
        if (mainContentArea == null) {
            System.out.println("mainContentArea is null. Check FXML fx:id and controller setup.");
            return;
        }

        // Clear the current content and set the new view
        mainContentArea.getChildren().clear();
        mainContentArea.getChildren().add(reportView);



        // Set anchors to center the view
        AnchorPane.setTopAnchor(reportView, 0.0);
        AnchorPane.setBottomAnchor(reportView, 0.0);
        AnchorPane.setLeftAnchor(reportView, 0.0);
        AnchorPane.setRightAnchor(reportView, 0.0);

        // Change the "Logout" button to become "Home"
        if (logoutButton != null) {
            logoutButton.setText("Home");
            logoutButton.setOnAction(event -> navigateToHome());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }


    // Helper method to load FXML into the main content area
    private void loadUI(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fixit/" + fxmlFile));
            Node node = loader.load();
            mainBorderPane.setCenter(node); // Set the loaded FXML to the center of BorderPane
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("MainContentArea: " + mainContentArea);
    }


    private void navigateToHome() {
        try {
            // Load the homepage view (e.g., home_page.fxml)
            Main.changeScene("/com/fixit/adminMenu.fxml");

            // Replace the main content area with the homepage view
            if (mainContentArea != null) {


                // Reset the button's text and action to "Logout"
                logoutButton.setText("Logout");
System.out.println("You're logged out");            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Helper method to show alerts to the user
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void LogOut(ActionEvent actionEvent) throws IOException {
        userLogOut(actionEvent);
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();
        String department = departmentComboBox.getValue();
        String firstName = firstNameField.getText();  // Get first name
        String lastName = lastNameField.getText();    // Get last name

        // Validation: Check if fields are filled
        if (username.isEmpty() || password.isEmpty() || role == null || department.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showAlert("Error", "All fields must be filled", Alert.AlertType.ERROR);
            return;
        }

        // Create a new user object with the form data
        User newUser = new User(0, username, password, role, firstName, lastName, department);

        // Save the user to the database
        try {
            userDAO.save(newUser);
            showAlert("Success", "User saved successfully!", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error", "Failed to save user: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            int selectedIndex = mytab.getSelectionModel().getSelectedIndex();
            if (selectedIndex <= -1) {
                return;
            }

            User selectedUser = mytab.getSelectionModel().getSelectedItem();
            userDAO.delete(selectedUser);  // Deleting the selected user

            UpdateTable(); // Refresh table after deleting
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception or show an error message
        }
    }
    // Update table method to display the data
    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));

        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));




        mytab.setItems(getDataUsers());
    }

    public void OnGoHomeClick(ActionEvent actionEvent) {
        navigateToHome();
    }

    public static ObservableList<User> getDataUsers(){

        UserDAO usedao;

        ObservableList<User> listfx = FXCollections.observableArrayList();

        try {
            usedao = new UserDAO();
            listfx.addAll(usedao.getAll());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }
}
