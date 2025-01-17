package com.fixit.Controller;
import com.fixit.Model.Incident;
import com.fixit.Model.IncidentDAO;
import com.fixit.Model.UserDAO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import static com.fixit.Controller.AuthController.userId;
import static com.fixit.Controller.AuthController.userLogOut;

public class TechController implements Initializable {


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

    @FXML
    private TableView<Incident> mytab1;

    @FXML
    private TableColumn <Incident, Integer> col_id1;
    @FXML
    private TableColumn <Incident, String> col_title1;

    @FXML
    private TableColumn <Incident, String> col_desc1;

    @FXML
    private TableColumn <Incident, String> col_type1;

    @FXML
    private TableColumn <Incident, String> col_priority1;

    @FXML
    public TableColumn<Incident, String> col_status1;
    @FXML
    public TableColumn <Incident, String> col_createdBy1;



    @FXML
    private TableView<Incident> mytab2;

    @FXML
    private TableColumn <Incident, Integer> col_id2;
    @FXML
    private TableColumn <Incident, String> col_title2;

    @FXML
    private TableColumn <Incident, String> col_desc2;

    @FXML
    private TableColumn <Incident, String> col_type2;

    @FXML
    private TableColumn <Incident, String> col_priority2;

    @FXML
    public TableColumn<Incident, String> col_status2;
    @FXML
    public TableColumn <Incident, String> col_createdBy2;



    // Constructeur du contrôleur
    private final IncidentDAO incidentDAO;

    public TechController() throws SQLException {
        this.incidentDAO = new IncidentDAO(); // Initialisation de l'instance DAO
    }
    int index = -1;
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        //TableView Controller
            index = mytab1.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
        Incident selectedIncident = mytab1.getSelectionModel().getSelectedItem();

            // If the incident is resolved, allow the employee to provide feedback
            if ("Resolved".equals(selectedIncident.getStatus()) && selectedIncident.getFeedback() == null ) {
//                    techNotesTextArea.setVisible(true);
//                    saveFeedbackButton.setVisible(true);
            } else {
//                    feedbackTextArea.setVisible(false);
//                    saveFeedbackButton.setVisible(false);
            }

        }

        public void LogOut(ActionEvent actionEvent) throws IOException {
            userLogOut(actionEvent);

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
        if (mytab1 != null || mytab2 != null) {
            initializeTableColumns(); // Set up table columns
            initializeTableColumns2(); // Set up table columns
            UpdateTable(mytab1, getOpenIncidents(), "Aucun incident ouvert pour l'instant.");
            UpdateTable(mytab2, getInProgressIncidents(), "Aucun incident en cours pour l'instant.");
            mytab1.refresh();
            mytab2.refresh();
        }
        else {
            System.err.println("TableView is not properly initialized. Skipping table setup.");
        }}

    private void initializeTableColumns() {

        // Set up table columns with PropertyValueFactory
        col_id1.setCellValueFactory(new PropertyValueFactory<>("incidentId"));
        col_title1.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_desc1.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_priority1.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col_type1.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_status1.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_createdBy1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedByUsername()));    }


        private void showAlert(String title, String message, Alert.AlertType alertType) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
        public void AssignToMe(ActionEvent actionEvent) {

            // Vérifie si un incident est sélectionné
        Incident selectedIncident = mytab1.getSelectionModel().getSelectedItem();

        if (selectedIncident == null) {
            showAlert("Aucun incident sélectionné", "Veuillez d'abord sélectionner un incident.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Récupérer l'ID de l'utilisateur connecté via l'attribut statique
            int currentUserId = userId; // Utilisation directe de votre attribut statique
            LocalDateTime resolutionDate = LocalDateTime.now();
            // Assigner l'incident et mettre à jour le statut
            IncidentDAO inciDAO = new IncidentDAO();
            boolean success = inciDAO.assignIncidentToUserAndUpdateStatus(
                    selectedIncident.getIncidentId(), currentUserId, "In Progress");


            // Gestion des résultats
            if (success) {
                showAlert("Succès", "Incident assigné et passé à 'En cours'.", Alert.AlertType.INFORMATION);
                UpdateTable(mytab1, getOpenIncidents(), "Aucun incident ouvert pour l'instant.");
                UpdateTable(mytab2, getInProgressIncidents(), "Aucun incident en cours pour l'instant.");

            } else {
                showAlert("Erreur", "L'incident n'a pas pu être assigné.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur s'est produite. Veuillez réessayer.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

        public static ObservableList<Incident> getInProgressIncidents() {
            ObservableList<Incident> list = FXCollections.observableArrayList();
            try {
                IncidentDAO inciDAO = new IncidentDAO();
                list.addAll(inciDAO.getInProgressIncidents());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return list;
        }

    public static ObservableList<Incident> getOpenIncidents() {
        ObservableList<Incident> openIncidentList = FXCollections.observableArrayList();
        try {
            openIncidentList.addAll(new IncidentDAO().getOpenIncidents());
            System.out.println(openIncidentList.size() + "########################## open incidents loaded.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "####################Erreur de récupération des incidents.").showAndWait());
        }
        return openIncidentList;
    }


    private void initializeTableColumns2() {

        col_id2.setCellValueFactory(new PropertyValueFactory<>("incidentId"));
        col_title2.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_desc2.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_priority2.setCellValueFactory(new PropertyValueFactory<>("priority"));
        col_type2.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_status2.setCellValueFactory(new PropertyValueFactory<>("status"));

        col_createdBy2.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedByUsername()));
    }

    public void MarkAsResolvedClick(ActionEvent actionEvent) {

        // Vérifie si un incident est sélectionné
        Incident selectedIncident2 = mytab2.getSelectionModel().getSelectedItem();
System.out.println(selectedIncident2.getIncidentId());
        if (selectedIncident2 == null) {
            showAlert("Aucun incident sélectionné", "Veuillez d'abord sélectionner un incident.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Récupérer l'ID de l'utilisateur connecté via l'attribut statique
            int currentUserId = userId; // Utilisation directe de votre attribut statique
            LocalDateTime resolutionDate = LocalDateTime.now();

            // Assigner l'incident et mettre à jour le statut
            IncidentDAO inciDAO = new IncidentDAO();
            boolean success = inciDAO.MarkAsResolved(
                    selectedIncident2.getIncidentId(), currentUserId, "Resolved",resolutionDate);

            // Gestion des résultats
            if (success) {
                showAlert("Succès", "Incident passé à 'Resolved'.", Alert.AlertType.INFORMATION);
                UpdateTable(mytab2, getInProgressIncidents(), "Aucun incident en cours pour l'instant."); // Mettre à jour la table
            } else {
                showAlert("Erreur", "L'incident n'a pas pu être mis à jour.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur s'est produite. Veuillez réessayer.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }


    private void UpdateTable(TableView<Incident> table, ObservableList<Incident> incidents, String noIncidentMessage) {
        if (!incidents.isEmpty()) {
            table.setItems(incidents);
        } else {
            table.setItems(FXCollections.emptyObservableList());
            showAlert("Aucun incident", noIncidentMessage, Alert.AlertType.INFORMATION);
        }
    }














//    public void UpdateTable() {
//        ObservableList<Incident> openIncidentList = getOpenIncidents();
//        System.out.println("Thread actif : " +openIncidentList.size());
//
//        if (!openIncidentList.isEmpty()) {
//            mytab1.setItems(openIncidentList);
//            System.out.println(openIncidentList.size() + " open incidents displayed.");
//        } else {
//            mytab1.setItems(FXCollections.emptyObservableList());
//            showAlert("Aucun incident", "Aucun incident ouvert pour l'instant.", Alert.AlertType.INFORMATION);
//        }
//    }
//
//
//    public void UpdateTable2() {
//
//        ObservableList<Incident> inprogressIncidentList = getInProgressIncidents();
//
//        if (!inprogressIncidentList.isEmpty()) {
//            mytab2.setItems(inprogressIncidentList);
//        } else {
//            mytab2.setItems(FXCollections.emptyObservableList());
//            showAlert("Aucun incident", "Aucun incident ouvert pour l'instant.", Alert.AlertType.INFORMATION);
//        }
//    }











}





