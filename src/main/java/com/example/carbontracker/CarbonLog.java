package com.example.carbontracker;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public class CarbonLog {
    @Id
    private String id;
//    private String userId;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String activityType;
    private double value;
    private double emission;
    private LocalDateTime timestamp;

    // Getters and Setters

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

//    public String getUserId() { return userId; }
//
//    public void setUserId(String userId) { this.userId = userId; }

    public String getActivityType() { return activityType; }

    public void setActivityType(String activityType) { this.activityType = activityType; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public double getEmission() { return emission; }

    public void setEmission(double emission) { this.emission = emission; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
