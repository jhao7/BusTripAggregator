package org.example.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.example.models.TapRecord;
import org.example.models.TripRecord;
import org.example.models.enums.TapType;
import org.example.models.enums.TripRecordCSVHeader;
import org.example.models.enums.TripStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static org.example.services.TapRecordCSVMapper.DATE_TIME_FORMATTER_TAP_RECORD;

@Service
public class TripRecordCSVBuilder {

    private static final String STOP_ID_1 = "Stop1";
    private static final String STOP_ID_2 = "Stop2";
    private static final String STOP_ID_3 = "Stop3";
    private static final String DOUBLE_DECIMAL_FORMAT = "%.2f";
    private static final String CURRENCY_SYMBOL = "$";
    private static final double CHARGE_AMOUNT_LOWER_FARE = 3.25;
    private static final double CHARGE_AMOUNT_MID_FARE = 5.50;
    private static final double CHARGE_AMOUNT_HIGHER_FARE = 7.30;
    private static final double CHARGE_AMOUNT_CANCELLED_FARE = 0.00;

    private static final Map<Pair<String, String>, Double> CHARGE_AMOUNT_COMPLETED_FARE_RULES = new HashMap<>() {{
        put(new ImmutablePair<>(STOP_ID_1, STOP_ID_2), Double.valueOf(CHARGE_AMOUNT_LOWER_FARE));
        put(new ImmutablePair<>(STOP_ID_2, STOP_ID_1), Double.valueOf(CHARGE_AMOUNT_LOWER_FARE));
        put(new ImmutablePair<>(STOP_ID_1, STOP_ID_3), Double.valueOf(CHARGE_AMOUNT_HIGHER_FARE));
        put(new ImmutablePair<>(STOP_ID_3, STOP_ID_1), Double.valueOf(CHARGE_AMOUNT_HIGHER_FARE));
        put(new ImmutablePair<>(STOP_ID_2, STOP_ID_3), Double.valueOf(CHARGE_AMOUNT_MID_FARE));
        put(new ImmutablePair<>(STOP_ID_3, STOP_ID_2), Double.valueOf(CHARGE_AMOUNT_MID_FARE));
    }};

    public List<TripRecord> buildTripRecordList(List<TapRecord> tapRecordList) {
        List<TripRecord> tripRecordList = new ArrayList<>();

        Map<Pair<String, String>, List<TapRecord>> tapRecordsGroupedByPanAndBusId =
            tapRecordList
                .stream()
                .sorted(Comparator.comparing(tapRecord -> tapRecord.getTapDateTimeUTC()))
                .collect(groupingBy(tapRecord -> new ImmutablePair<>(tapRecord.getPan(), tapRecord.getBusId())));

        for (var entry : tapRecordsGroupedByPanAndBusId.entrySet()) {
            Pair<String, String> compoundKey = entry.getKey();
            List<TapRecord> tapRecord = tapRecordsGroupedByPanAndBusId.get(compoundKey);

            ListIterator<TapRecord> listIterator = tapRecord.listIterator();
            while (listIterator.hasNext()) {
                TapRecord t1 = listIterator.next();
                if (t1.getTapType().equals(TapType.ON)) {
                    String chargeAmount;
                    TripRecord tripRecord;
                    if (listIterator.hasNext()) {
                        TapRecord t2 = listIterator.next();
                        if (t2.getTapType().equals(TapType.OFF)) {
                            Long durationSecs =
                                    calculateDurationInSeconds(t1.getTapDateTimeUTC(), t2.getTapDateTimeUTC());
                            if (t1.getStopId().equals(t2.getStopId())) {
                                chargeAmount = formatDoubleWithTwoDecimals(calculateCancelledChargeAmount());
                                tripRecord = buildCancelledTripRecord(t1, t2, durationSecs, chargeAmount);
                            } else {
                                chargeAmount = formatDoubleWithTwoDecimals(
                                        calculateCompletedChargeAmount(t1.getStopId(), t2.getStopId()));
                                tripRecord = buildCompletedTripRecord(t1, t2, durationSecs, chargeAmount);
                            }
                        } else {
                            chargeAmount = formatDoubleWithTwoDecimals(calculateIncompleteChargeAmount(t1.getStopId()));
                            tripRecord = buildIncompleteTripRecord(t1, chargeAmount);
                        }
                    } else {
                        chargeAmount = formatDoubleWithTwoDecimals(calculateIncompleteChargeAmount(t1.getStopId()));
                        tripRecord = buildIncompleteTripRecord(t1, chargeAmount);
                    }

                    tripRecordList.add(tripRecord);
                } else {
                    // Skip to the next TapRecord
                }
            }
        }

        return tripRecordList;
    }

    public void generateTripRecordCSV(List<TripRecord> tripRecord) throws IOException {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("trips.csv"));

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setDelimiter(", ")
                    .setHeader(TripRecordCSVHeader.class)
                    .get();

            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

            for (TripRecord tr : tripRecord) {
                String startedDateTime = formatLocalDateTime(tr.getStartedDateTimeUTC());
                String finishedDateTime =
                        tr.getFinishedDateTimeUTC() == null ? "" : formatLocalDateTime(tr.getFinishedDateTimeUTC());
                csvPrinter.printRecord(
                        startedDateTime, finishedDateTime, tr.getDurationSecs(), tr.getFromStopId(), tr.getToStopId(),
                        tr.getChargeAmount(), tr.getCompanyId(), tr.getBusId(), tr.getPan(), tr.getTripStatus());
            }

            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDoubleWithTwoDecimals(double chargeAmount) {
        return CURRENCY_SYMBOL + String.format(DOUBLE_DECIMAL_FORMAT, chargeAmount);
    }

    private Long calculateDurationInSeconds(LocalDateTime tapOnDateTimeUTC, LocalDateTime tapOffDateTimeUTC) {
        return Long.valueOf(Duration.between(tapOnDateTimeUTC, tapOffDateTimeUTC).getSeconds());
    }

    private Double calculateCompletedChargeAmount(String tapOnStopId, String tapOffStopId) {
        return CHARGE_AMOUNT_COMPLETED_FARE_RULES.get(new ImmutablePair<>(tapOnStopId, tapOffStopId));
    }

    private Double calculateCancelledChargeAmount() {
        return Double.valueOf(CHARGE_AMOUNT_CANCELLED_FARE);
    }

    private Double calculateIncompleteChargeAmount(String stopId) {
        return stopId.equals(STOP_ID_2) ? Double.valueOf(CHARGE_AMOUNT_MID_FARE) : Double.valueOf(CHARGE_AMOUNT_HIGHER_FARE);
    }

    private TripRecord buildCompletedTripRecord(TapRecord tapOnRecord, TapRecord tapOffRecord, Long durationSecs,
                                                String chargeAmount) {
        TripRecord tripRecord = new TripRecord();

        tripRecord.setStartedDateTimeUTC(tapOnRecord.getTapDateTimeUTC());
        tripRecord.setFinishedDateTimeUTC(tapOffRecord.getTapDateTimeUTC());
        tripRecord.setDurationSecs(durationSecs);
        tripRecord.setFromStopId(tapOnRecord.getStopId());
        tripRecord.setToStopId(tapOffRecord.getStopId());
        tripRecord.setChargeAmount(chargeAmount);
        tripRecord.setCompanyId(tapOnRecord.getCompanyId());
        tripRecord.setBusId(tapOnRecord.getBusId());
        tripRecord.setPan(tapOnRecord.getPan());
        tripRecord.setTripStatus(TripStatus.COMPLETED);

        return tripRecord;
    }

    private TripRecord buildCancelledTripRecord(TapRecord tapOnRecord, TapRecord tapOffRecord, Long durationSecs,
                                                String chargeAmount) {
        TripRecord tripRecord = new TripRecord();

        tripRecord.setStartedDateTimeUTC(tapOnRecord.getTapDateTimeUTC());
        tripRecord.setFinishedDateTimeUTC(tapOffRecord.getTapDateTimeUTC());
        tripRecord.setDurationSecs(durationSecs);
        tripRecord.setFromStopId(tapOnRecord.getStopId());
        tripRecord.setToStopId(tapOffRecord.getStopId());
        tripRecord.setChargeAmount(chargeAmount);
        tripRecord.setCompanyId(tapOnRecord.getCompanyId());
        tripRecord.setBusId(tapOnRecord.getBusId());
        tripRecord.setPan(tapOnRecord.getPan());
        tripRecord.setTripStatus(TripStatus.CANCELLED);

        return tripRecord;
    }

    private TripRecord buildIncompleteTripRecord(TapRecord tapOnRecord, String chargeAmount) {
        TripRecord tripRecord = new TripRecord();

        tripRecord.setStartedDateTimeUTC(tapOnRecord.getTapDateTimeUTC());
        tripRecord.setFromStopId(tapOnRecord.getStopId());
        tripRecord.setChargeAmount(chargeAmount);
        tripRecord.setCompanyId(tapOnRecord.getCompanyId());
        tripRecord.setBusId(tapOnRecord.getBusId());
        tripRecord.setPan(tapOnRecord.getPan());
        tripRecord.setTripStatus(TripStatus.INCOMPLETE);

        return tripRecord;
    }

    private String formatLocalDateTime(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_TAP_RECORD).format(localDateTime);
    }

}
