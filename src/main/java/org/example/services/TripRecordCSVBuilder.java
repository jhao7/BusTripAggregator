package org.example.services;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.example.models.TapRecord;
import org.example.models.TripRecord;
import org.example.models.enums.TapType;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class TripRecordCSVBuilder {

    public List<TripRecord> calculateTripRecordList(List<TapRecord> tapRecordList) {
        List<TripRecord> tripRecordList = new ArrayList<>();

        Map<Pair<String, String>, List<TapRecord>> tapRecordsGroupedByPanAndBusId =
                tapRecordList.stream()
                        .sorted(Comparator.comparing(tapRecord -> tapRecord.getTapDateTimeUTC()))
                        .collect(groupingBy(tapRecord -> new ImmutablePair<>(tapRecord.getPan(), tapRecord.getBusId())));

        for (var entry : tapRecordsGroupedByPanAndBusId.entrySet()) {
            Pair<String, String> compoundKey = entry.getKey();
            List<TapRecord> s = tapRecordsGroupedByPanAndBusId.get(compoundKey);

            ListIterator<TapRecord> listIterator = s.listIterator();
            while (listIterator.hasNext()) {
                TapRecord t = listIterator.next();
                if (t.getTapType().equals(TapType.ON)) {
                    if (listIterator.hasNext()) {
                        TapRecord t2 = listIterator.next();
                        if (t2.getTapType().equals(TapType.OFF)) {
                            // TODO: Calculate fare for completed or cancelled trips.

                        } else {
                            // TODO: Calculate fare for incomplete trips.
                        }
                    } else {
                        // TODO: Calculate fare for incomplete trips.
                    }
                } else {
                    // TODO: Skip to the next TapRecord.
                }
            }
        }

        return tripRecordList;
    }
}
