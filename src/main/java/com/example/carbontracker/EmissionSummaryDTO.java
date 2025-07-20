package com.example.carbontracker;

public class EmissionSummaryDTO {
    private String label;
    private double totalCarbonFootprint;

    public EmissionSummaryDTO(String label, double totalCarbonFootprint) {
        this.label = label;
        this.totalCarbonFootprint = totalCarbonFootprint;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getTotalCarbonFootprint() {
        return totalCarbonFootprint;
    }

    public void setTotalCarbonFootprint(double totalCarbonFootprint) {
        this.totalCarbonFootprint = totalCarbonFootprint;
    }
}
