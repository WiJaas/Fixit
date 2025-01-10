package com.fixit.Controller;
import com.fixit.Controller.EmployeeController;
import com.fixit.Main;
import com.fixit.Model.UserDAO;
import com.fixit.Model.User;
import java.util.List;

import com.fixit.Util.Roles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AuthController {
    public static int userId ;

    @FXML
    private Button login;
    @FXML
    private Label wronglogin;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;

    public AuthController() {
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

//        Main m = new Main();
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            List<User> users = userDAO.getAll();

            for (User user : users) {

                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {

                    userId = user.getId();
                    // Determine role and navigate accordingly
                    switch (user.getRole()) {


                        case Roles.TECHNICIAN:

                            // Navigate to Technician Dashboard
                            Main.changeScene("techDashboard.fxml");
                            wronglogin.setText("Welcome Technician!");
                            break;
                        case Roles.ADMIN:
                            wronglogin.setText("Welcome Admin!");
                            // Navigate to Admin Dashboard
                            Main.changeScene("adminMenu.fxml");


                            break;
                        case Roles.EMPLOYEE:
                            wronglogin.setText("Welcome Employee!");
                            // Navigate to Employee Dashboard
                         Main.changeScene("employeeHome.fxml");
//                            try {
//                                // Récupérer uniquement l'ID de l'utilisateur depuis la base de données
//                                int userId = userDAO.findUserIdByUsernameAndPassword(username, password);
//                                System.out.println(("#############" +userId));
//
//                                // Charger la vue EmployeeHome
//                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fixit/EmployeeHome.fxml"));
//                                Scene scene = new Scene(loader.load());
//
//                                // Obtenez le contrôleur EmployeeHomeController
//                                EmployeeController employeeHomeController = loader.getController();
//
//                                // Passer l'ID utilisateur
//                                employeeHomeController.setUserId(String.valueOf(userId));
//
//                                // Afficher EmployeeHome
//                                Stage stage = new Stage();
//                                stage.setScene(scene);
//                                stage.show();
//
//                                // Fermer la fenêtre de connexion
//                                Stage loginStage = (Stage) usernameField.getScene().getWindow();
//                                loginStage.close();
//
//                            } catch (Exception e) {
//                                // Gérer les erreurs d'authentification ou d'exécution
//                                System.out.println("Erreur lors de la connexion : " + e.getMessage());
//                            }
//

                            break;

                        default:
                            wronglogin.setText("Unknown role. Access denied.");
                            break;
                    }
                    return; // Exit after successful login
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // If no user matches, login fails
        wronglogin.setText("Invalid username or password!");
    }



    public static void userLogOut(ActionEvent actionEvent) throws IOException {

        Main.changeScene("login.fxml");

    }


    public void userRegister(ActionEvent event) throws IOException {
        Main m = new Main();
//        m.changeScene("Register.fxml");


    }
}



