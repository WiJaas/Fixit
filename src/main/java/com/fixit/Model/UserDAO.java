package com.fixit.Model;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDAO extends BaseDAO<User> {
    public UserDAO() throws SQLException {
        super();
    }

    public void save(User user) throws SQLException {
        // Updated query to include 'first_name' and 'last_name' fields
        String sql = "INSERT INTO user (username, password, role, first_name, last_name, department) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Setting values for each placeholder in the query
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getFirstName());  // Adding first_name
            statement.setString(5, user.getLastName());   // Adding last_name
            statement.setString(6, user.getDepartment());

            // Execute the update (insert the user into the database)
            statement.executeUpdate();
        }
    }

    @Override
    public User getOne(int id) throws SQLException {
        String query = "SELECT id_user, username, password, role, first_name, last_name, department FROM user WHERE id_user=?";
        User user = null; // Initialiser à null

        try (
                PreparedStatement preparedStatement = this.connection.prepareStatement(query)
        ) {
            // Injection des paramètres dans la requête
            preparedStatement.setInt(1, id);

            // Exécution de la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // S'il y a un résultat, peupler l'objet User
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id_user"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("role"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("department")
                    );
                }
            }
        } catch (SQLException e) {
            // Log et propagation de l'erreur
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
            throw e;
        }

        return user; // Retournera null si aucun utilisateur n'est trouvé
    }



    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT id_user, username, password, role, first_name, last_name, department FROM user";

        try (
                PreparedStatement preparedStatement = this.connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id_user"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("department")
                ));
            }
        } catch (SQLException e) {
            // Log et propagation de l'exception
            System.err.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            throw e;
        }
        return users;
    }

//    public int findUserIdByUsernameAndPassword(String username, String password) throws Exception {
//        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt("id"); // Récupérer uniquement la colonne ID
//                } else {
//                    throw new Exception("Utilisateur introuvable avec ces identifiants.");
//                }
//            }
//        }
//    }


    @Override
    public void delete(User user) throws SQLException {
        String query = "DELETE FROM user WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        }
    }


    @Override
    public void update(User object) throws SQLException {

        String req = "UPDATE user SET username=?, password=?, role=?, first_name=?, last_name=?, department=? WHERE id_user=?";
        try (PreparedStatement statement = connection.prepareStatement(req)) {
            // Setting values for each placeholder in the query
            statement.setString(1, object.getUsername());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getRole());
            statement.setString(4, object.getFirstName());
            statement.setString(5, object.getLastName());
            statement.setString(6, object.getDepartment());
            statement.setInt(7, object.getId());


            // Execute the update (insert the user into the database)
            statement.executeUpdate();
        }
    }
}