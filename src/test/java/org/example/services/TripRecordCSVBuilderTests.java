package org.example.services;

import org.example.models.TapRecord;
import org.example.models.TripRecord;
import org.example.models.enums.TapType;
import org.example.models.enums.TripStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.example.services.TapRecordCSVMapper.DATE_TIME_FORMATTER_TAP_RECORD;

public class TripRecordCSVBuilderTests {

    private TripRecordCSVBuilder tripRecordCSVBuilder;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_TAP_RECORD);

    @BeforeEach
    public void setUp() {
        tripRecordCSVBuilder = new TripRecordCSVBuilder();
    }

    @Test
    public void testBuildingTripRecordListFromTapRecordList() {
        List<TapRecord> tapRecordList = buildTapRecordListResult();
        List<TripRecord> tripRecordList = tripRecordCSVBuilder.buildTripRecordList(tapRecordList);
        Assertions.assertTrue(tripRecordList.equals(buildTripRecordListResult()));
    }

    @Test
    public void testBuildingTripRecordListFromUnorderedTapRecordList() {
        List<TapRecord> tapRecordList = buildUnorderedTapRecordListResult();
        List<TripRecord> tripRecordList = tripRecordCSVBuilder.buildTripRecordList(tapRecordList);
        Assertions.assertTrue(tripRecordList.equals(buildTripRecordListResult()));
    }

    @Test
    public void testBuildingBulkTripRecordListFromBulkTapRecordList() {
        List<TapRecord> tapRecordList = buildBulkTapRecordListResult();
        List<TripRecord> tripRecordList = tripRecordCSVBuilder.buildTripRecordList(tapRecordList);
        Assertions.assertTrue(tripRecordList.equals(buildBulkTripRecordListResult()));
    }

    @Test
    public void testBuildingBulkTripRecordListFromUnorderedBulkTapRecordList() {
        List<TapRecord> tapRecordList = buildUnorderedBulkTapRecordListResult();
        List<TripRecord> tripRecordList = tripRecordCSVBuilder.buildTripRecordList(tapRecordList);
        Assertions.assertTrue(tripRecordList.equals(buildBulkTripRecordListResult()));
    }


    private List<TripRecord> buildTripRecordListResult() {
        List<TripRecord> tripRecordList = new ArrayList<>();

        tripRecordList.add(new TripRecord(LocalDateTime.parse("23-01-2023 08:00:00", formatter),
                LocalDateTime.parse("23-01-2023 08:02:00", formatter), 120L,
                "Stop1", "Stop1", "$0.00", "Company1", "Bus37",
                "4111111111111111", TripStatus.CANCELLED));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("22-01-2023 13:00:00", formatter),
                LocalDateTime.parse("22-01-2023 13:05:00", formatter), 300L,
                "Stop1", "Stop2", "$3.25", "Company1", "Bus37",
                "5500005555555559", TripStatus.COMPLETED));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("22-01-2023 09:20:00", formatter),
                null, null, "Stop3",  null, "$7.30",
                "Company1", "Bus36", "4111111111111111", TripStatus.INCOMPLETE));

        return tripRecordList;
    }

    private List<TripRecord> buildBulkTripRecordListResult() {
        List<TripRecord> tripRecordList = new ArrayList<>();

        tripRecordList.add(new TripRecord(LocalDateTime.parse("23-01-2023 08:00:00", formatter),
                LocalDateTime.parse("23-01-2023 08:02:00", formatter), 120L,
                "Stop1", "Stop1", "$0.00", "Company1", "Bus37",
                "4111111111111111", TripStatus.CANCELLED));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("25-01-2023 13:35:00", formatter),
                LocalDateTime.parse("25-01-2023 13:39:00", formatter), 240L,
                "Stop3", "Stop2", "$5.50", "Company1", "Bus36",
                "5500005555555559", TripStatus.COMPLETED));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("25-01-2023 16:25:00", formatter),
                null, null, "Stop2",  null, "$5.50",
                "Company1", "Bus36", "5500005555555559", TripStatus.INCOMPLETE));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("22-01-2023 13:00:00", formatter),
                LocalDateTime.parse("22-01-2023 13:05:00", formatter), 300L,
                "Stop1", "Stop2", "$3.25", "Company1", "Bus37",
                "5500005555555559", TripStatus.COMPLETED));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("24-01-2023 16:35:00", formatter),
                null, null, "Stop2",  null, "$5.50",
                "Company1", "Bus37", "5500005555555559", TripStatus.INCOMPLETE));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("24-01-2023 16:48:00", formatter),
                LocalDateTime.parse("24-01-2023 16:49:00", formatter), 60L, "Stop2",
                "Stop3", "$5.50",
                "Company1", "Bus37", "5500005555555559", TripStatus.COMPLETED));
        tripRecordList.add(new TripRecord(LocalDateTime.parse("22-01-2023 09:20:00", formatter),
                null, null, "Stop3",  null, "$7.30",
                "Company1", "Bus36", "4111111111111111", TripStatus.INCOMPLETE));

        return tripRecordList;
    }


    private List<TapRecord> buildTapRecordListResult() {
        // Matches src/main/resources/csvs/TapRecords.csv
        List<TapRecord> tapRecordList = new ArrayList<>();

        tapRecordList.add(new TapRecord(1L, LocalDateTime.parse("22-01-2023 13:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(2L, LocalDateTime.parse("22-01-2023 13:05:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(3L, LocalDateTime.parse("22-01-2023 09:20:00", formatter), TapType.ON,
                "Stop3", "Company1", "Bus36", "4111111111111111"));
        tapRecordList.add(new TapRecord(4L, LocalDateTime.parse("23-01-2023 08:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(5L, LocalDateTime.parse("23-01-2023 08:02:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(6L, LocalDateTime.parse("24-01-2023 16:30:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));

        return tapRecordList;
    }

    private List<TapRecord> buildUnorderedTapRecordListResult() {
        // Matches src/main/resources/csvs/UnorderedTapRecords.csv
        List<TapRecord> tapRecordList = new ArrayList<>();

        tapRecordList.add(new TapRecord(1L, LocalDateTime.parse("22-01-2023 13:05:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(2L, LocalDateTime.parse("24-01-2023 16:30:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(3L, LocalDateTime.parse("23-01-2023 08:02:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(4L, LocalDateTime.parse("22-01-2023 13:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(5L, LocalDateTime.parse("22-01-2023 09:20:00", formatter), TapType.ON,
                "Stop3", "Company1", "Bus36", "4111111111111111"));
        tapRecordList.add(new TapRecord(6L, LocalDateTime.parse("23-01-2023 08:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "4111111111111111"));

        return tapRecordList;
    }

    private List<TapRecord> buildBulkTapRecordListResult() {
        // Matches src/main/resources/csvs/BulkTapRecords.csv
        List<TapRecord> tapRecordList = new ArrayList<>();

        tapRecordList.add(new TapRecord(1L, LocalDateTime.parse("22-01-2023 13:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(2L, LocalDateTime.parse("22-01-2023 13:05:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(3L, LocalDateTime.parse("24-01-2023 16:30:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(4L, LocalDateTime.parse("24-01-2023 16:35:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(5L, LocalDateTime.parse("24-01-2023 16:38:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(6L, LocalDateTime.parse("24-01-2023 16:45:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(7L, LocalDateTime.parse("24-01-2023 16:48:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(8L, LocalDateTime.parse("24-01-2023 16:49:00", formatter), TapType.OFF,
                "Stop3", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(9L, LocalDateTime.parse("25-01-2023 11:04:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(10L, LocalDateTime.parse("25-01-2023 13:30:00", formatter), TapType.OFF,
                "Stop3", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(11L, LocalDateTime.parse("25-01-2023 13:35:00", formatter), TapType.ON,
                "Stop3", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(12L, LocalDateTime.parse("25-01-2023 13:39:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(13L, LocalDateTime.parse("25-01-2023 16:25:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(14L, LocalDateTime.parse("24-01-2023 16:48:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(15L, LocalDateTime.parse("22-01-2023 09:20:00", formatter), TapType.ON,
                "Stop3", "Company1", "Bus36", "4111111111111111"));
        tapRecordList.add(new TapRecord(16L, LocalDateTime.parse("23-01-2023 08:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(17L, LocalDateTime.parse("23-01-2023 08:02:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(18L, LocalDateTime.parse("24-01-2023 09:20:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(19L, LocalDateTime.parse("24-01-2023 10:00:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus36", "4111111111111111"));
        tapRecordList.add(new TapRecord(20L, LocalDateTime.parse("24-01-2023 10:08:00", formatter), TapType.OFF,
                "Stop3", "Company1", "Bus36", "4111111111111111"));

        return tapRecordList;
    }

    private List<TapRecord> buildUnorderedBulkTapRecordListResult() {
        // Matches src/main/resources/csvs/UnorderedBulkTapRecords.csv
        List<TapRecord> tapRecordList = new ArrayList<>();

        tapRecordList.add(new TapRecord(1L, LocalDateTime.parse("22-01-2023 13:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(2L, LocalDateTime.parse("24-01-2023 16:35:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(3L, LocalDateTime.parse("24-01-2023 10:00:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus36", "4111111111111111"));
        tapRecordList.add(new TapRecord(4L, LocalDateTime.parse("22-01-2023 13:05:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(5L, LocalDateTime.parse("24-01-2023 16:45:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(6L, LocalDateTime.parse("24-01-2023 16:48:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(7L, LocalDateTime.parse("23-01-2023 08:00:00", formatter), TapType.ON,
                "Stop1", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(8L, LocalDateTime.parse("25-01-2023 13:30:00", formatter), TapType.OFF,
                "Stop3", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(9L, LocalDateTime.parse("25-01-2023 13:35:00", formatter), TapType.ON,
                "Stop3", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(10L, LocalDateTime.parse("22-01-2023 09:20:00", formatter), TapType.ON,
                "Stop3", "Company1", "Bus36", "4111111111111111"));
        tapRecordList.add(new TapRecord(11L, LocalDateTime.parse("25-01-2023 13:39:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(12L, LocalDateTime.parse("23-01-2023 08:02:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(13L, LocalDateTime.parse("24-01-2023 16:30:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(14L, LocalDateTime.parse("24-01-2023 16:38:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(15L, LocalDateTime.parse("24-01-2023 09:20:00", formatter), TapType.OFF,
                "Stop2", "Company1", "Bus37", "4111111111111111"));
        tapRecordList.add(new TapRecord(16L, LocalDateTime.parse("24-01-2023 16:49:00", formatter), TapType.OFF,
                "Stop3", "Company1", "Bus37", "5500005555555559"));
        tapRecordList.add(new TapRecord(17L, LocalDateTime.parse("25-01-2023 11:04:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(18L, LocalDateTime.parse("24-01-2023 10:08:00", formatter), TapType.OFF,
                "Stop3", "Company1", "Bus36", "4111111111111111"));
        tapRecordList.add(new TapRecord(19L, LocalDateTime.parse("25-01-2023 16:25:00", formatter), TapType.ON,
                "Stop2", "Company1", "Bus36", "5500005555555559"));
        tapRecordList.add(new TapRecord(20L, LocalDateTime.parse("24-01-2023 16:48:00", formatter), TapType.OFF,
                "Stop1", "Company1", "Bus36", "5500005555555559"));

        return tapRecordList;
    }
}
