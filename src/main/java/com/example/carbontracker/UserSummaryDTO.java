package com.example.carbontracker;

import java.util.List;

public class UserSummaryDTO {
    public String userId; // actually user's email
    public double totalEmission;
    public List<ActivityRecord> activities;

    public static class ActivityRecord {
        public String activityType;
        public double value;
        public double emission;
        public String timestamp;

        public ActivityRecord(String activityType, double value, double emission, String timestamp) {
            this.activityType = activityType;
            this.value = value;
            this.emission = emission;
            this.timestamp = timestamp;
        }
    }
}
