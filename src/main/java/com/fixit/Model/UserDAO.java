package com.fixit.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        User user = null;
        String sql = "SELECT * FROM user WHERE id_user = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
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
        }
        return user;
    }



    @Override
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<User>();
        String sql = "SELECT * FROM user";  // Query to select all users

        // Using try-with-resources to ensure statement and resultSet are closed properly
        try (Statement statement = this.connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                // Adding each user to the list
                userList.add(new User(
                        resultSet.getInt("id_user"),  // Assuming 'id' is the first column
                        resultSet.getString("username"),  // Assuming 'username' is the second column
                        resultSet.getString("password"),  // Assuming 'password' is the third column
                        resultSet.getString("role"),  // Assuming 'role' is the fourth column
                        resultSet.getString("first_name"),  // Assuming 'first_name' is the fifth column
                        resultSet.getString("last_name"),   // Assuming 'last_name' is the sixth column
                        resultSet.getString("department")  // Assuming 'department' is the seventh column
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions properly
            throw e;  // Rethrow exception after logging it
        }

        return userList;
    }

    public int findUserIdByUsernameAndPassword(String username, String password) throws Exception {
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Récupérer uniquement la colonne ID
                } else {
                    throw new Exception("Utilisateur introuvable avec ces identifiants.");
                }
            }
        }
    }


    @Override
    public void delete(User user) throws SQLException {
        String query = "DELETE FROM user WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        }
    }


}
