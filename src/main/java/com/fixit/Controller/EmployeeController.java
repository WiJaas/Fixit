package com.fixit.Controller;
import com.fixit.Main;
import com.fixit.Model.Incident;
import com.fixit.Model.IncidentDAO;
import com.fixit.Model.UserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.fixit.Controller.AuthController.userId;
import static com.fixit.Controller.AuthController.userLogOut;

public class EmployeeController implements Initializable {

    private StringProperty title = new SimpleStringProperty(this, "title", "");
    public final StringProperty titleProperty() {
        return title;
    }

    public final void setTitle(String value) {
        titleProperty().setValue(value);
    }

    public final String getTitle() {
        return title.getValue();
    }



    public BorderPane mainBorderPane;
    public Button HomeButton;
    public Button logoutButton;
    public ComboBox<String> statusBox;

    @FXML
    private TableView<Incident> mytab;

    @FXML
    private TableColumn <Incident, Integer> col_id;
    @FXML
    private TableColumn <Incident, String> col_title;

    @FXML
   private TableColumn <Incident, String> col_desc;

    @FXML
   private TableColumn <Incident, String> col_type;

    @FXML
   private TableColumn <Incident, String> col_priority;

    @FXML
    public TableColumn<Incident, String> col_status;

    @FXML
    private TextField incidentIdField;
    // Champs liés à l'interface utilisateur (via FXML)
    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;


    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private ComboBox<String> priorityBox;

    @FXML
    private TableColumn<Incident, String> col_feedback;
    @FXML
    private TextArea feedbackTextArea;
    @FXML
    private Button saveFeedbackButton;


        // Référence à une instance d'IncidentDAO pour gérer les données
    private final IncidentDAO incidentDAO;


    // Constructeur du contrôleur
    public EmployeeController() throws SQLException {
        this.incidentDAO = new IncidentDAO(); // Initialisation de l'instance DAO
    }


    @FXML
    private void handleSaveIncident(ActionEvent event) {
        try {
            // Valider que tous les champs requis sont remplis
            if (titleField.getText().isEmpty() ||
                    descriptionField.getText().isEmpty() ||
                    typeBox.getValue() == null ||
                    priorityBox.getValue() == null){

                // Afficher une alerte si des champs sont manquants
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de validation");
                alert.setHeaderText("Champs manquants");
                alert.setContentText("Veuillez remplir tous les champs obligatoires.");
                alert.showAndWait();
                return;
            }

            // Récupérer les valeurs des champs
            String title = titleField.getText();
            String description = descriptionField.getText();
            String priority = priorityBox.getValue();
            String type = typeBox.getValue();



            // L'ID utilisateur connecté (par AuthController)
            LocalDateTime creationDate = LocalDateTime.now();

            int createdBy = userId;


            // Create a new user object with the form data
            Incident newIncident = new Incident(0,title, description,"Open", priority,type,createdBy,null,creationDate,null,null);
           System.out.println(newIncident.getCreatedBy());
            // Save the user to the database
            try {
                incidentDAO.save(newIncident);
                showAlert("Success", "Incident saved successfully!", Alert.AlertType.INFORMATION);
                if (getDataIncidents() != null) {
                    mytab.setItems(FXCollections.observableArrayList(getDataIncidents()));
                }


            }
            catch (SQLException e) {
                showAlert("Error", "Failed to save incident: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }


                // Réinitialiser les champs après succès
                resetIncidentForm();

        }catch (Exception e) {
            // Gérer toutes les autres erreurs
            showAlert("Erreur", "Une erreur inattendue s'est produite : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        }

    public static ObservableList<Incident> getDataIncidents() {
        ObservableList<Incident> listfx = FXCollections.observableArrayList();
        try {
            IncidentDAO inciDAO = new IncidentDAO();
                listfx.addAll(inciDAO.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listfx;
    }

    // Méthode pour réinitialiser les champs
    private void resetIncidentForm() {
        titleField.clear();
        descriptionField.clear();
        typeBox.setValue(null);
        priorityBox.setValue(null);
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

    private void navigateToHome() {
        try {
            // Load the homepage view (e.g., home_page.fxml)
            Main.changeScene("/com/fixit/employeeHome.fxml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnGoHomeClick(ActionEvent actionEvent) {
        navigateToHome();
    }

     String text ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Safely initialize UI components

        UserDAO useDAO = null;
        try {
            useDAO = new UserDAO();
            text= (useDAO.getOne(userId)).getUsername();
            setTitle(text);
            System.out.println(text);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(text);

        // Defer setting up the table to avoid NullPointerException
        if (mytab != null) {
            initializeTableColumns(); // Set up table columns
            UpdateTable();
        } else {
            System.err.println("TableView is not properly initialized. Skipping table setup.");
        }}


    private void initializeTableColumns() {

        // Set up table columns with PropertyValueFactory
        col_id.setCellValueFactory(new PropertyValueFactory<>("incidentId"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));


    }
    // Update UpdateTable method
    public void UpdateTable() {
        ObservableList<Incident> incidentList = getDataIncidents();
        if (incidentList != null) {
            mytab.setItems(FXCollections.observableArrayList(incidentList));
        } else {
            System.err.println("No incidents available to display in the table.");
        }
    }














    //TableView Controller

    int index = -1;
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = mytab.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        Incident selectedIncident = mytab.getSelectionModel().getSelectedItem();

        // If the incident is resolved, allow the employee to provide feedback
        if ("Resolved".equals(selectedIncident.getStatus()) && selectedIncident.getFeedback() == null ) {
            feedbackTextArea.setVisible(true);
            saveFeedbackButton.setVisible(true);
        } else {
            feedbackTextArea.setVisible(false);
            saveFeedbackButton.setVisible(false);
        }

    }
    @FXML
    private void handleSaveFeedback(ActionEvent event) {
        try {
            // Retrieve the selected incident
            Incident selectedIncident = mytab.getSelectionModel().getSelectedItem();

            if (selectedIncident == null) {
                showAlert("Error", "Please select an incident to provide feedback.", Alert.AlertType.ERROR);
                return;
            }

            // Get the feedback text
            String feedback = feedbackTextArea.getText();

            // Check if feedback is empty
            if (feedback.isEmpty()) {
                showAlert("Error", "Feedback cannot be empty!", Alert.AlertType.ERROR);
                return;
            }

            // Set feedback to the selected incident
            selectedIncident.setFeedback(feedback);

            // Update the feedback in the database
            incidentDAO.updateFeedback(selectedIncident);

            // Refresh the table
            initializeTableColumns();
            UpdateTable();

            // Clear the feedback text area and hide the feedback controls
            feedbackTextArea.clear();
            feedbackTextArea.setVisible(false);
            saveFeedbackButton.setVisible(false);

            showAlert("Success", "Feedback saved successfully!", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error", "Failed to save feedback: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    }





