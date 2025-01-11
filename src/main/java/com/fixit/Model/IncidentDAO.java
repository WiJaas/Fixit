package com.fixit.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IncidentDAO extends BaseDAO<Incident> {

    public IncidentDAO() throws SQLException {
        // Appelle le constructeur parent pour initialiser la connexion
        super();
    }

    @Override
    public void save(Incident incident) throws SQLException {
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



            statement.setTimestamp(8, Timestamp.valueOf(incident.getCreationDate()));

            if (incident.getCreationDate() != null) {
                statement.setTimestamp(9, Timestamp.valueOf(incident.getCreationDate()));
            } else {
                // Ne rien envoyer ou utiliser DEFAULT de la base de données
                statement.setNull(9, java.sql.Types.TIMESTAMP);
            }

            if (incident.getFeedback() == null) {
                statement.setNull(10, java.sql.Types.VARCHAR);
            } else {
                statement.setString(10, incident.getFeedback());
            }
            statement.setString(10, incident.getFeedback());
            // Execute the update (insert the user into the database)

            if (statement.executeUpdate() > 0) {
                System.out.println("Incident successfully saved: " +incident);
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
        return List.of();
    }

    @Override
    public void delete(Incident object) throws SQLException {

    }



}