package org.example;

import com.google.common.io.Resources;
import org.example.services.TapRecordCSVMapper;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        TapRecordCSVMapper trm = new TapRecordCSVMapper();
        trm.mapCSV(Resources.getResource("csvs/TapRecords.csv").getPath());
    }
}