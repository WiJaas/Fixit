package com.fixit.Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;



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

}
