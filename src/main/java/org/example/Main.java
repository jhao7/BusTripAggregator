package org.example;

import com.google.common.io.Resources;
import org.example.models.TapRecord;
import org.example.services.TapRecordCSVMapper;
import org.example.services.TripRecordCSVBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        TapRecordCSVMapper trm = new TapRecordCSVMapper();
        //List<TapRecord> list = trm.mapCSV(Resources.getResource("csvs/TapRecords.csv").getPath());
        List<TapRecord> list = trm.mapCSV(Resources.getResource("csvs/UnOrderedTapRecords.csv").getPath());
        TripRecordCSVBuilder trb = new TripRecordCSVBuilder();
        trb.calculateTripRecordList(list);
    }
}