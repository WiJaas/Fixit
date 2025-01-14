package com.fixit.Model;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.fixit.Controller.AuthController.userId;

public class IncidentDAO extends BaseDAO<Incident> {

    public IncidentDAO() throws SQLException {
        // Appelle le constructeur parent pour initialiser la connexion
        super();
    }

    @Override
    public void save(Incident incident) throws SQLException {


        String query = "INSERT INTO incident (title, description, status, priority, type, created_by, assigned_to, creation_date, resolution_date, feedback) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, incident.getTitle());
            statement.setString(2, incident.getDescription());
            statement.setString(3, incident.getStatus());
            statement.setString(4, incident.getPriority());
            statement.setString(5, incident.getType());

            statement.setInt(6, incident.getCreatedBy());


            if (incident.getAssignedTo() == null) {
                statement.setNull(7, java.sql.Types.VARCHAR);
            } else {
                statement.setInt(7, incident.getAssignedTo());
            }


            // Handle 'creation_date' - if null, set it as NULL in the database
            if (incident.getCreationDate() != null) {
                statement.setTimestamp(8, Timestamp.valueOf(incident.getCreationDate()));
            } else {
                statement.setNull(8, Types.TIMESTAMP);  // Set NULL for creation_date if it's null
            }

            // Handle 'resolution_date' - if null, set it as NULL in the database
            if (incident.getResolutionDate() != null) {
                statement.setTimestamp(9, Timestamp.valueOf(incident.getResolutionDate()));
            } else {
                statement.setNull(9, Types.TIMESTAMP);  // Set NULL for resolution_date if it's null
            }

            if (incident.getFeedback() == null) {
                statement.setNull(10, java.sql.Types.VARCHAR);
            } else {
                statement.setString(10, incident.getFeedback());
            }
            statement.setString(10, incident.getFeedback());
            // Execute the update (insert the user into the database)

            if (statement.executeUpdate() > 0) {
                System.out.println("Incident successfully saved: " + incident);
            } else {
                System.err.println("Incident save failed: " + incident);
            }
        } catch (SQLException e) {
            System.err.println("SQL error during incident save: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException closeEx) {
                    System.err.println("Error closing statement: " + closeEx.getMessage());
                }
            }
        }
    }


    @Override
    public Incident getOne(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Incident> getAll() throws SQLException {
        List<Incident> incidents = new ArrayList<>();
        String query = "SELECT incident_id , title, description, status, priority, type, created_by, assigned_to, creation_date, resolution_date, feedback FROM incident WHERE created_by = ?";

        try (
                PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        ) {
//            int userid = 27; // Ajout de la définition de 'userId'. Remplacez par un paramètre ou une méthode adaptée.
            preparedStatement.setInt(1, userId); //
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {


                    LocalDateTime creationDate = null;
                    LocalDateTime resolutionDate = null;

                    if (resultSet.getTimestamp("creation_date") != null) {
                        creationDate = resultSet.getTimestamp("creation_date").toLocalDateTime();
                    }
                    if (resultSet.getTimestamp("resolution_date") != null) {
                        resolutionDate = resultSet.getTimestamp("resolution_date").toLocalDateTime();
                    }
                    incidents.add(new Incident(
                            resultSet.getInt("incident_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getString("status"),
                            resultSet.getString("priority"),
                            resultSet.getString("type"),
                            resultSet.getInt("created_by"),
                            resultSet.getInt("assigned_to"),
                            creationDate,  // Affectation sécurisée sur creation_date
                            resolutionDate,  //
                            resultSet.getString("feedback")
                    ));
                    //        System.out.println("Incident#################");
//        System.out.println("Incident#################"+incident.getTitle());
//        System.out.println("Incident#################"+incident.getDescription());
//        System.out.println("Incident#################"+incident.getStatus());
//
//        System.out.println("Incident#################"+incident.getPriority());
//        System.out.println("Incident#################"+incident.getType());
//        System.out.println("Incident#################"+incident.getCreatedBy());
//        System.out.println("Incident#################"+incident.getAssignedTo());
//        System.out.println("Incident#################"+incident.getCreationDate());
//        System.out.println("Incident#################"+incident.getResolutionDate());
//        System.out.println("Incident#################"+incident.getFeedback());

                }

            }
        } catch (SQLException e) {
            // Log et propagation de l'exception
            System.err.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            throw e;
        }
        return incidents;
    }

    public void update(Incident incident) throws SQLException {
        String query = "UPDATE incident SET title = ?, description = ?, status = ?, priority = ?, type = ?,created_by = ?, assigned_to = ?, creation_date = ?, resolution_date = ?, feedback = ? WHERE incident_id = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(query)) {

            // Setting the parameters for the update query
            statement.setString(1, incident.getTitle());
            statement.setString(2, incident.getDescription());
            statement.setString(3, incident.getStatus());
            statement.setString(4, incident.getPriority());
            statement.setString(5, incident.getType());
            statement.setInt(6, incident.getCreatedBy());
            statement.setInt(7, incident.getAssignedTo());
            statement.setTimestamp(8, Timestamp.valueOf(incident.getCreationDate()));
            statement.setTimestamp(9, incident.getResolutionDate() != null ?
                    Timestamp.valueOf(incident.getResolutionDate()) : null);
            statement.setString(10, incident.getFeedback());
            statement.setInt(11, incident.getIncidentId());

            // Executing the update query
            statement.executeUpdate();
        }
    }


    @Override
    public void delete(Incident object) throws SQLException {

    }


    public void updateFeedback(Incident incident) throws SQLException {
        String query = "UPDATE incident SET feedback = ? WHERE incident_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            // Setting the parameters for the update query
            statement.setString(1, incident.getFeedback());
            statement.setInt(2, incident.getIncidentId());

            // Executing the update query
            statement.executeUpdate();
        }


    }

    public List<Incident> getOpenIncidents() throws SQLException {
        List<Incident> openIncidents = new ArrayList<>();
        String query = "SELECT * FROM incident WHERE status = 'Open'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Directement créer un objet Incident
                openIncidents.add(new Incident(
                        rs.getInt("incident_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getString("type"),
                        rs.getInt("created_By"),
                        rs.getObject("assigned_To", Integer.class),
                        rs.getTimestamp("creation_date") != null
                                ? rs.getTimestamp("creation_date").toLocalDateTime() : null,
                        rs.getTimestamp("resolution_date") != null
                                ? rs.getTimestamp("resolution_date").toLocalDateTime() : null,
                        rs.getString("feedback")
                ));
            }
        }

        return openIncidents;
    }

    public boolean assignIncidentToUserAndUpdateStatus(int incidentId, int userId, String status) throws SQLException {
        String query = "UPDATE incident SET assigned_to = ?, status = ? WHERE incident_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);    // Assigne l'utilisateur
            stmt.setString(2, status); // Met à jour le statut
            stmt.setInt(3, incidentId); // Identifie l'incident
            return stmt.executeUpdate() > 0; // Retourne true si la mise à jour réussit
        }
    }

    public boolean MarkAsResolved(int incidentId, int userId, String status) throws SQLException {
        String query = "UPDATE incident SET assigned_to = ?, status = ? WHERE incident_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);    // Assigne l'utilisateur
            stmt.setString(2, status); // Met à jour le statut
            stmt.setInt(3, incidentId); // Identifie l'incident
            return stmt.executeUpdate() > 0; // Retourne true si la mise à jour réussit
        }
    }


    public List<Incident> getInProgressIncidents() throws SQLException {
        List<Incident> openIncidents = new ArrayList<>();
        String query = "SELECT * FROM incident WHERE status = 'In Progress' or status ='Resolved'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Directement créer un objet Incident
                openIncidents.add(new Incident(
                        rs.getInt("incident_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getString("type"),
                        rs.getInt("created_By"),
                        rs.getObject("assigned_To", Integer.class),
                        rs.getTimestamp("creation_date") != null
                                ? rs.getTimestamp("creation_date").toLocalDateTime() : null,
                        rs.getTimestamp("resolution_date") != null
                                ? rs.getTimestamp("resolution_date").toLocalDateTime() : null,
                        rs.getString("feedback")
                ));
            }
        }

        return openIncidents;
    }
}


