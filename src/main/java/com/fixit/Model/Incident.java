package com.fixit.Model;


import java.time.LocalDateTime;

public class Incident {

    private int incidentId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String type;
    private int createdBy;
    private Integer assignedTo; // Peut être null
    private LocalDateTime creationDate;
    private String resolutionDate;
    private String feedback;

    public Incident(int id, String title, String description, String status, String priority, String type, int createdBy, Integer assignedTo, LocalDateTime dateCreated, String resolutionDate, String feedback) {

        this.incidentId = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.type = type;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.creationDate = dateCreated;
        this.resolutionDate = resolutionDate;
        this.feedback = feedback;

    }

    // Getters et Setters

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}