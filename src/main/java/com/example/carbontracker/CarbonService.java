package com.example.carbontracker;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CarbonService {

    private final CarbonLogRepository carbonLogRepository;
    private final UserRepository userRepository;

    public CarbonService(CarbonLogRepository carbonLogRepository, UserRepository userRepository) {
        this.carbonLogRepository = carbonLogRepository;
        this.userRepository = userRepository;
    }

    // Fetch daily emission trend (used for line charts)
    public Map<String, Double> getDailyEmissionTrend(String email) {
        List<CarbonLog> logs = carbonLogRepository.findByEmail(email);
        Map<String, Double> trend = new TreeMap<>();
        for (CarbonLog log : logs) {
            String date = log.getTimestamp().toLocalDate().toString();
            trend.put(date, trend.getOrDefault(date, 0.0) + log.getEmission());
        }
        return trend;
    }

    // Summary report by range: daily, monthly, yearly, category-wise
    public List<EmissionSummaryDTO> getSummaryByRange(String email, String range) {
        switch (range.toLowerCase()) {
            case "daily": return carbonLogRepository.getDailySummary(email);
            case "monthly": return carbonLogRepository.getMonthlySummary(email);
            case "yearly": return carbonLogRepository.getYearlySummary(email);
            case "category": return carbonLogRepository.getActivityTypeSummary(email);
            default: throw new IllegalArgumentException("Invalid range: " + range);
        }
    }

    // Log a new carbon activity
    public CarbonLog logCarbon(CarbonLogDTO dto) {
        double factor = getEmissionFactor(dto.getActivityType().toLowerCase());

        CarbonLog log = new CarbonLog();
        log.setEmail(dto.getEmail()); // Assuming you have `email` in DTO & Entity
        log.setActivityType(dto.getActivityType());
        log.setValue(dto.getValue());
        log.setEmission(dto.getValue() * factor);
        log.setTimestamp(LocalDateTime.now());

        return carbonLogRepository.save(log);
    }

    // User activity summary using email (for frontend summary page)
    public UserSummaryDTO getUserSummaryByEmail(String email) {
        List<CarbonLog> logs = carbonLogRepository.findByEmail(email);

        double totalEmission = 0;
        List<UserSummaryDTO.ActivityRecord> activities = new ArrayList<>();

        for (CarbonLog log : logs) {
            totalEmission += log.getEmission();
            activities.add(new UserSummaryDTO.ActivityRecord(
                    log.getActivityType(),
                    log.getValue(),
                    log.getEmission(),
                    log.getTimestamp().toString()
            ));
        }

        UserSummaryDTO summary = new UserSummaryDTO();
        summary.userId = email;
        summary.totalEmission = Math.round(totalEmission * 100.0) / 100.0;
        summary.activities = activities;

        return summary;
    }

    // Leaderboard sorted by total emission (descending)
    public List<LeaderboardEntryDTO> getLeaderboard() {
        List<User> allUsers = userRepository.findAll();
        List<LeaderboardEntryDTO> leaderboard = new ArrayList<>();

        for (User user : allUsers) {
            List<CarbonLog> logs = carbonLogRepository.findByEmail(user.getEmail());
            double totalEmission = logs.stream()
                    .mapToDouble(CarbonLog::getEmission)
                    .sum();
            leaderboard.add(new LeaderboardEntryDTO(
                    user.getEmail(),
                    Math.round(totalEmission * 100.0) / 100.0
            ));
        }

        leaderboard.sort((a, b) -> Double.compare(b.totalEmission, a.totalEmission));
        return leaderboard;
    }
    public List<CarbonLog> getLogsByEmail(String email) {
        return carbonLogRepository.findByEmail(email);
    }
    // Emission factors based on activity type
    private double getEmissionFactor(String activityType) {
        return switch (activityType) {
            // Transportation
            case "car" -> 0.21;
            case "bus" -> 0.08;
            case "bike" -> 0.05;
            case "train" -> 0.04;
            case "plane" -> 0.15;
            case "scooter" -> 0.06;
            case "walk", "cycle", "bicycle" -> 0.0;

            // Energy
            case "electricity" -> 0.82;

            // Food
            case "meat" -> 27.0;
            case "dairy" -> 13.0;
            case "vegetarian" -> 2.0;
            case "vegan" -> 1.5;

            // Goods
            case "clothing" -> 0.025;
            case "electronics" -> 0.1;
            case "furniture" -> 0.05;

            // Fuels
            case "gasoline", "petrol" -> 2.31;
            case "diesel" -> 2.68;
            case "cng" -> 1.92;
            case "lng" -> 1.75;
            case "lpg" -> 1.51;
            case "kerosene" -> 2.53;
            case "jetfuel" -> 2.50;
            case "biodiesel" -> 1.80;
            case "ethanol" -> 1.50;

            // Default for unknown items
            default -> 0.1;
        };
    }
}
