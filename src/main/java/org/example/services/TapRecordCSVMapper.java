package org.example.services;

import org.example.models.TapRecord;
import org.example.models.enums.TapType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TapRecordCSVMapper {

    public static final String DATE_TIME_FORMATTER_TAP_RECORD = "dd-MM-yyyy HH:mm:ss";

    public List<TapRecord> buildTapRecordList(String TapRecordCSVFilePath) {
        List<TapRecord> tapRecordList = new ArrayList<>();

        try {
            File inputFile = new File(TapRecordCSVFilePath);
            InputStream inputFileStream = new FileInputStream(inputFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFileStream));

            tapRecordList = br.lines().skip(1).map(line -> buildTapRecord(line)).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return tapRecordList;
    }

    private TapRecord buildTapRecord(String line) {
        String[] csvRecord = line.split(",");
        TapRecord tapRecord = new TapRecord();

        if (csvRecord[0] != null && csvRecord[0].trim().length() > 0) {
            tapRecord.setId(Long.parseLong(csvRecord[0].trim()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_TAP_RECORD);
        if (csvRecord[1] != null && csvRecord[1].trim().length() > 0) {
            tapRecord.setTapDateTimeUTC(LocalDateTime.parse(csvRecord[1].trim(), formatter));
        }

        if (csvRecord[2] != null && csvRecord[2].trim().length() > 0) {
            tapRecord.setTapType(TapType.valueOf(csvRecord[2].trim()));
        }

        if (csvRecord[3] != null && csvRecord[3].trim().length() > 0) {
            tapRecord.setStopId(csvRecord[3].trim());
        }

        if (csvRecord[4] != null && csvRecord[4].trim().length() > 0) {
            tapRecord.setCompanyId(csvRecord[4].trim());
        }

        if (csvRecord[5] != null && csvRecord[5].trim().length() > 0) {
            tapRecord.setBusId(csvRecord[5].trim());
        }

        if (csvRecord[6] != null && csvRecord[6].trim().length() > 0) {
            tapRecord.setPan(csvRecord[6].trim());
        }

        return tapRecord;
    }
}
