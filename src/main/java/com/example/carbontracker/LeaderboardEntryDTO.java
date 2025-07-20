package com.example.carbontracker;

public class LeaderboardEntryDTO {
    public String email;
    public double totalEmission;

    public LeaderboardEntryDTO(String email, double totalEmission) {
        this.email = email;
        this.totalEmission = totalEmission;
    }
}
