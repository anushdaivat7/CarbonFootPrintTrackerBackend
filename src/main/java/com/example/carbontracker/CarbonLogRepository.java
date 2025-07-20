package com.example.carbontracker;

import com.example.carbontracker.CarbonLog;
import com.example.carbontracker.EmissionSummaryDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarbonLogRepository extends MongoRepository<CarbonLog, String> {

    List<CarbonLog> findByEmail(String email); // ✅ for raw logs

    @Aggregation(pipeline = {
            "{ $match: { email: ?0 } }",
            "{ $group: { _id: { $dayOfMonth: '$timestamp' }, total: { $sum: '$emission' } } }",
            "{ $sort: { '_id': 1 } }",
            "{ $project: { label: { $toString: '$_id' }, totalCarbonFootprint: '$total' } }"
    })
    List<EmissionSummaryDTO> getDailySummary(String email);

    @Aggregation(pipeline = {
            "{ $match: { email: ?0 } }",
            "{ $group: { _id: { $month: '$timestamp' }, total: { $sum: '$emission' } } }",
            "{ $sort: { '_id': 1 } }",
            "{ $project: { label: { $toString: '$_id' }, totalCarbonFootprint: '$total' } }"
    })
    List<EmissionSummaryDTO> getMonthlySummary(String email);

    @Aggregation(pipeline = {
            "{ $match: { email: ?0 } }",
            "{ $group: { _id: { $year: '$timestamp' }, total: { $sum: '$emission' } } }",
            "{ $sort: { '_id': 1 } }",
            "{ $project: { label: { $toString: '$_id' }, totalCarbonFootprint: '$total' } }"
    })
    List<EmissionSummaryDTO> getYearlySummary(String email);

    @Aggregation(pipeline = {
            "{ $match: { email: ?0 } }",
            "{ $group: { _id: '$activityType', total: { $sum: '$emission' } } }",
            "{ $sort: { total: -1 } }",
            "{ $project: { label: '$_id', totalCarbonFootprint: '$total' } }"
    })
    List<EmissionSummaryDTO> getActivityTypeSummary(String email);
}
