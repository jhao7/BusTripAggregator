package org.example.services;

import org.example.models.TapRecord;
import org.example.models.enums.TapType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.example.services.TapRecordCSVMapper.DATE_TIME_FORMATTER_TAP_RECORD;

public class TapRecordCSVMapperTests {

    private TapRecordCSVMapper tapRecordCSVMapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_TAP_RECORD);

    @BeforeEach
    public void setUp() {
        tapRecordCSVMapper = new TapRecordCSVMapper();
    }

    @Test
    public void testOrderedTapRecordCSVMapping() {
        String uri = "csvs/OrderedTapRecords.csv";
        List<TapRecord> tapRecordList = tapRecordCSVMapper.buildTapRecordList(uri);
        Assertions.assertTrue(tapRecordList.equals(buildTapRecordListResult()));
    }

    @Test
    public void testOrderedBulkTapRecordCSVMapping() {
        String uri = "csvs/OrderedBulkTapRecords.csv";
        List<TapRecord> tapRecordList = tapRecordCSVMapper.buildTapRecordList(uri);
        Assertions.assertTrue(tapRecordList.equals(buildBulkTapRecordListResult()));
    }

    private List<TapRecord> buildTapRecordListResult() {
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

    private List<TapRecord> buildBulkTapRecordListResult() {
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

}
