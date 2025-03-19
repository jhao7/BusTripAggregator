package org.example.services;

import org.example.models.TapRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TapRecordCSVMapper {

    public List<TapRecord> mapCSV(String TapRecordCSVFilePath) {
        List<TapRecord> tapRecordList = new ArrayList<>();

        try {
            File inputFile = new File(TapRecordCSVFilePath);
            InputStream inputFileStream = new FileInputStream(inputFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFileStream));

            tapRecordList = br.lines().skip(1).map(line -> convertCSVtoTapRecord(line)).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return tapRecordList;
    }

    private TapRecord convertCSVtoTapRecord(String line) {
        String[] csvRecord = line.split(",");
        TapRecord tapRecord = new TapRecord();

        if (csvRecord[0] != null && csvRecord[0].trim().length() > 0) {
            tapRecord.setId(Long.parseLong(csvRecord[0]));
        }

        //TODO: Add other columns' mapping.

        return tapRecord;
    }
}
