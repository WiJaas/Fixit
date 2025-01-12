package com.fixit.Controller;

import com.fixit.Main;
import com.fixit.Model.Incident;
import com.fixit.Model.IncidentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.fixit.Controller.AuthController.userLogOut;

public class EmployeeController {
    public BorderPane mainBorderPane;
    public Button HomeButton;
    public Button logoutButton;
    public ComboBox<String> statusBox;
    // Champs liés à l'interface utilisateur (via FXML)
    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private ComboBox<String> priorityBox;



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
            String type = typeBox.getValue();
            String priority = priorityBox.getValue();



            // L'ID utilisateur connecté (par AuthController)
            int createdBy = AuthController.userId;
            LocalDateTime creationDate = LocalDateTime.now();


            // Create a new user object with the form data
            Incident newIncident = new Incident(0,title, description,"Open", priority,type,createdBy,null,creationDate,null,null);
           System.out.println(newIncident.getCreatedBy());
            // Save the user to the database
            try {
                incidentDAO.save(newIncident);
                showAlert("Success", "Incident saved successfully!", Alert.AlertType.INFORMATION);
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

            // Replace the main content area with the homepage view
//            if (mainBorderPane != null) {
//
//
//                // Reset the button's text and action to "Logout"
////                logoutButton.setText("Logout");
////System.out.println("You're logged out");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OnGoHomeClick(ActionEvent actionEvent) {
        navigateToHome();
    }

}

