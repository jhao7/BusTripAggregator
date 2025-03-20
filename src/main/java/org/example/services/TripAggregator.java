package org.example.services;

import com.google.common.io.Resources;
import jakarta.annotation.PostConstruct;
import org.example.models.TapRecord;
import org.example.models.TripRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripAggregator {

    private TapRecordCSVMapper tapRecordCSVMapper;

    private TripRecordCSVBuilder tripRecordCSVBuilder;

    public TripAggregator(TapRecordCSVMapper tapRecordCSVMapper, TripRecordCSVBuilder tripRecordCSVBuilder) {
        this.tapRecordCSVMapper = tapRecordCSVMapper;
        this.tripRecordCSVBuilder = tripRecordCSVBuilder;
    }

    @PostConstruct
    public void init() throws Exception {
        // Please replace "csvs/TapRecords.csv" with other resource names to test other taps csv files.
        // E.g "csv/BulkTapRecords.csv", "csv/UnorderedTapRecords.csv" or "csv/UnorderedBulkTapRecords.csv"
        List<TapRecord> tapRecordList = tapRecordCSVMapper.buildTapRecordList(
                Resources.getResource("csvs/TapRecords.csv").getPath());
        List<TripRecord> tripRecordList = tripRecordCSVBuilder.buildTripRecordList(tapRecordList);
        tripRecordCSVBuilder.generateTripRecordCSV(tripRecordList);
    }
}
