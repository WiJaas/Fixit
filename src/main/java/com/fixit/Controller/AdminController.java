package com.fixit.Controller;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;

import com.fixit.Main;
import com.fixit.Model.Incident;
import com.fixit.Model.IncidentDAO;
import com.fixit.Model.User;
import com.fixit.Model.UserDAO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;


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
    private TextField techIdField;

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
    @FXML
    private TableView<Incident> mytabr;
    @FXML
    private TableColumn<Incident, Integer> col_idr;
    @FXML
    private TableColumn<Incident, String> col_titler;
    @FXML
    private TableColumn<Incident, String> col_descr;
    @FXML
    private TableColumn<Incident, String> col_typer;
    @FXML
    private TableColumn<Incident, String> col_priorityr;
    @FXML
    private TableColumn<Incident, String> col_statusr;
    @FXML
    private TableColumn<Incident, String> col_createdByr;
    @FXML
    private TableColumn<Incident, Integer> col_TechId;
    @FXML
    private TableColumn<Incident, String> col_TechName;
    @FXML
    private TableColumn<Incident, String> col_createdDate;
    @FXML
    private TableColumn<Incident, String> col_resolutionDate;
    @FXML
    private TableColumn<Incident, String> col_Feedback;

    @FXML
    private Button exportUsersButton; // Button to export user data
    @FXML
    private Button exportIncidentsButton; // Button to export incident data


    private UserDAO userDAO;


    public AdminController() {
        try {
            this.userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

            Main.changeScene("/com/fixit/userCreation_page.fxml");

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


    public void OnEditButtonClick(ActionEvent event) {
        try {
            UserDAO userDAO = new UserDAO();

            int selectedIndex = mytab.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                // Aucun client n'a été sélectionné, rien à faire
                return;
            }
            User selectedUser = mytab.getSelectionModel().getSelectedItem();
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
        try {
            // Vérifiez si les deux TableView sont bien initialisés
            if (mytab != null) {

                UpdateTable();
                initializeTableColumns();
            } else {
                System.err.println("TableViews (mytab ou mytabr) non initialisées. Configuration ignorée.");

            }
            if (mytabr != null) {
                UpdateTabler();
                initializeTableColumnsr();
            } else {
                System.err.println("TableViews (mytab ou mytabr) non initialisées. Configuration ignorée.");
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Une erreur inattendue s'est produite lors de l'initialisation des tables : " + e.getMessage());
        }
    }
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


    private void initializeTableColumnsr() {
     System.out.println(col_idr);
        col_idr.setCellValueFactory(new PropertyValueFactory<>("incidentId"));
        col_titler.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_descr.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_priorityr.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col_typer.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_statusr.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_createdByr.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedByUsername()));
        col_TechId.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        col_TechName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAssignedToUsername()));
        col_createdDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        col_resolutionDate.setCellValueFactory(new PropertyValueFactory<>("resolutionDate"));
        col_Feedback.setCellValueFactory(new PropertyValueFactory<>("feedback"));
    }


    public static ObservableList<Incident> getReportIncidents() {
        ObservableList<Incident> reportIncidentList = FXCollections.observableArrayList();
        System.out.println(reportIncidentList.size() + "########################## adminCont line 455");

        try {
            reportIncidentList.addAll(new IncidentDAO().getIncidents());
            System.out.println(reportIncidentList.size() + "########################## report incidents loaded.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "####################Erreur de récupération des incidents.").showAndWait());
        }
        return reportIncidentList;

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
    // Update UpdateTable method
    public void UpdateTabler() {
        ObservableList<Incident> incidentsList = getReportIncidents();
        System.out.println(incidentsList+ "########################## Line 468");

        if (incidentsList != null) {
            mytabr.setItems(FXCollections.observableArrayList(incidentsList));
        } else {
            System.err.println("No users available to display in the table.");
        }
    }





    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    // Method to export user data to a CSV file
    @FXML
    public void exportUsersToCSV(ActionEvent event) {
        String fileName = "users_report.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ID,Username,Password,Role,First Name,Last Name,Department\n");
            for (User user : getDataUsers()) {
                writer.write(user.getId() + "," +
                        user.getUsername() + "," +
                        user.getPassword() + "," +
                        user.getRole() + "," +
                        user.getFirstName() + "," +
                        user.getLastName() + "," +
                        user.getDepartment() + "\n");
            }
            showAlert("Success", "User report generated: " + fileName, Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to export incident data to a CSV file
    @FXML
    public void exportIncidentsToCSV(ActionEvent event) {
        String fileName = "incidents_report.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("ID,Title,Description,Type,Priority,Status,Created By,Technician ID,Technician Name,Created Date,Resolution Date,Feedback\n");
            for (Incident incident : getReportIncidents()) {
                writer.write(incident.getIncidentId() + "," +
                        incident.getTitle() + "," +
                        incident.getDescription() + "," +
                        incident.getType() + "," +
                        incident.getPriority() + "," +
                        incident.getStatus() + "," +
                        incident.getCreatedBy() + "," +
                        incident.getAssignedTo() + "," +
                        incident.getAssignedToUsername() + "," +
                        incident.getCreationDate() + "," +
                        incident.getResolutionDate() + "," +
                        incident.getFeedback() + "\n");
            }
            showAlert("Success", "Incident report generated: " + fileName, Alert.AlertType.INFORMATION);


        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void generateReport(int technicianId) {
        try {
            // Create an instance of IncidentDAO
            IncidentDAO incidentDAO = new IncidentDAO();

            // Fetch incidents assigned to the technician
            List<Incident> technicianIncidents = incidentDAO.getIncidentsByTechnician(technicianId);

            // Create a Document instance for the PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Technician_Report_" + technicianId + ".pdf"));

            // Open the document to write content
            document.open();

            // Set up the title and general text
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Incidents Assigned to Technician ID: " + technicianId, titleFont);
            document.add(title);
            document.add(new Paragraph("----------------------------------------------------------"));

            // Check if incidents were found
            if (technicianIncidents.isEmpty()) {
                document.add(new Paragraph("No incidents found for Technician ID: " + technicianId));
            } else {
                // Iterate over the incidents and add them to the PDF
                for (Incident incident : technicianIncidents) {
                    document.add(new Paragraph("Incident ID: " + incident.getIncidentId()));
                    document.add(new Paragraph("Title: " + incident.getTitle()));
                    document.add(new Paragraph("Status: " + incident.getStatus()));
                    document.add(new Paragraph("Priority: " + incident.getPriority()));
                    document.add(new Paragraph("Creation Date: " + incident.getCreationDate()));
                    document.add(new Paragraph("Resolution Date: " + incident.getResolutionDate()));
                    document.add(new Paragraph("Feedback: " + incident.getFeedback()));
                    document.add(new Paragraph("-----------------------------"));
                }
            }

            // Close the document
            document.close();
            System.out.println("Report generated successfully.");
        } catch (SQLException e) {
            System.err.println("Error while generating report: " + e.getMessage());
            e.printStackTrace();
        } catch (DocumentException | FileNotFoundException e) {
            System.err.println("Error while creating the PDF document: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onGenerateReportButtonClick() {
        try {
            int technicianId = Integer.parseInt(techIdField.getText()); // Get technician ID from TextField
            generateReport(technicianId); // Call the method to generate the report
        } catch (NumberFormatException e) {
            System.err.println("Invalid Technician ID entered.");
        }
    }
}