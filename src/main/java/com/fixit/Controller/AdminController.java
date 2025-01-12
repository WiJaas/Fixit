package com.fixit.Controller;

import com.fixit.Main;
import com.fixit.Model.User;
import com.fixit.Model.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import static com.fixit.Controller.AuthController.userLogOut;

public class AdminController  implements Initializable {
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
    public Button HomeButton;
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
//    @FXML
//    private void Click_CreateUser() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fixit/userCreation_page.fxml"));
//            Node userCreationView = loader.load();
//
//            // Check if mainContentArea is null
//            if (mainContentArea == null) {
//                System.out.println("mainContentArea is null. Check FXML fx:id and controller setup.");
//                return;
//            }
//
//            // Clear the current content and set the new view
//            mainContentArea.getChildren().clear();
//            mainContentArea.getChildren().add(userCreationView);
//
//
//
//            // Set anchors to center the view
//            AnchorPane.setTopAnchor(userCreationView, 0.0);
//            AnchorPane.setBottomAnchor(userCreationView, 0.0);
//            AnchorPane.setLeftAnchor(userCreationView, 0.0);
//            AnchorPane.setRightAnchor(userCreationView, 0.0);
//
//            // Change the "Logout" button to become "Home"
//            if (logoutButton != null) {
//                logoutButton.setText("Home");
//                logoutButton.setOnAction(event -> navigateToHome());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
@FXML
    public void goToReportsPage(ActionEvent event) {
        try {
            Parent reportPage = FXMLLoader.load(getClass().getResource("/com/fixit/report_page.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(reportPage));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the report page.");
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
        //Retrieve Infos from the fxml form
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
            // Update TableView (use mutable list)
            if (getDataUsers() != null) {
                mytab.setItems(FXCollections.observableArrayList(getDataUsers()));
            }
        } catch (SQLException e) {
            showAlert("Error", "Failed to save user: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void navigateToHome() {
        try {
            // Load the homepage view (e.g.
            // home_page.fxml)
            Main.changeScene("/com/fixit/userCreation_page.fxml");

            // Replace the main content area with the homepage view
            if (mainContentArea != null) {


                // Reset the button's text and action to "Logout"
//                logoutButton.setText("Logout");
//System.out.println("You're logged out");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnGoHomeClick(ActionEvent actionEvent) {
        navigateToHome();
    }


   //TableView Controller

    int index = -1;
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = mytab.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        User selectedUser = mytab.getSelectionModel().getSelectedItem();
        userIdField.setText(String.valueOf(selectedUser.getId()));
        usernameField.setText(selectedUser.getUsername());
        firstNameField.setText(selectedUser.getFirstName());
        lastNameField.setText(String.valueOf(selectedUser.getLastName()));
        departmentComboBox.setValue(String.valueOf(selectedUser.getDepartment()));
        roleComboBox.setValue(String.valueOf(selectedUser.getRole()));

    }

//needs update
    public void OnEditButtonClick(ActionEvent event) {
        try {
            UserDAO userDAO = new UserDAO();

            int selectedIndex = mytab.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                // Aucun client n'a été sélectionné, rien à faire
                return;
            }

            User selectedUser = mytab.getSelectionModel().getSelectedItem();
            int id = selectedUser.getId();
            String password = selectedUser.getPassword();
            String username = this.usernameField.getText();
            if (passwordField.getText() != null){
                 password = this.passwordField.getText();
            }
            String role = this.roleComboBox.getValue();
            String firstName = this.firstNameField.getText();
            String lastName = this.lastNameField.getText();
            String department = this.departmentComboBox.getValue();



            // Met à jour l'objet client avec les nouvelles valeurs
            selectedUser.setUsername(username);
            selectedUser.setPassword(password);
            selectedUser.setRole(role);
            selectedUser.setFirstName(firstName);
            selectedUser.setLastName(lastName);
            selectedUser.setDepartment(department);

            // Met à jour le client dans la base de données
            userDAO.update(selectedUser);

            // Clear TableView fields
            userIdField.clear();
            usernameField.clear();
            passwordField.clear();
            firstNameField.clear();
            lastNameField.clear();
            departmentComboBox.setValue(null);
            roleComboBox.setValue(null);

            // Met à jour la table
            UpdateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Safely initialize UI components
        if (mainContentArea != null) {
            System.out.println("MainContentArea is initialized.");
        }
        // Defer setting up the table to avoid NullPointerException
        if (mytab != null) {
            initializeTableColumns(); // Set up table columns
            UpdateTable();
        } else {
            System.err.println("TableView is not properly initialized. Skipping table setup.");
        }}
        private void initializeTableColumns() {

            // Set up table columns with PropertyValueFactory
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
            col_department.setCellValueFactory(new PropertyValueFactory<>("department"));
            col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    }



    @FXML
    protected void onDeleteButtonClick() {
        try {
            int myIndex = mytab.getSelectionModel().getSelectedIndex();

            // Ensure an item is selected before attempting deletion
            if (myIndex < 0) {
                showAlert("Error", "No user selected for deletion!", Alert.AlertType.ERROR);
                return;
            }

            // Fetch item to delete
            User selectedUser = mytab.getItems().get(myIndex);
            if (selectedUser == null) {
                showAlert("Error", "Failed to retrieve selected user for deletion.", Alert.AlertType.ERROR);
                return;
            }

            // Call DAO method to delete
            userDAO.delete(selectedUser);

            // Clear TableView fields
            userIdField.clear();
            usernameField.clear();
            passwordField.clear();
            firstNameField.clear();
            lastNameField.clear();
            departmentComboBox.setValue(null);
            roleComboBox.setValue(null);

            // Update TableView (use mutable list)
            if (getDataUsers() != null) {
                mytab.setItems(FXCollections.observableArrayList(getDataUsers()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete user: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Update getDataUsers method
    public static ObservableList<User> getDataUsers() {
        ObservableList<User> listfx = FXCollections.observableArrayList();
        try {
            UserDAO userDAO = new UserDAO();
            listfx.addAll(userDAO.getAll());

            // Ensure filtered list is wrapped in a mutable ObservableList
            listfx = FXCollections.observableArrayList(listfx.filtered(user -> {
                String role = user.getRole() != null ? user.getRole().trim().toLowerCase() : "";
                return role.equals("employee") || role.equals("technician");
            }));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listfx;
    }

    // Update UpdateTable method
    public void UpdateTable() {
        ObservableList<User> userList = getDataUsers();
        if (userList != null) {
            mytab.setItems(FXCollections.observableArrayList(userList));
        } else {
            System.err.println("No users available to display in the table.");
        }
    }

}