package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.models.enums.TripStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripRecord {

    // TODO: Fix started/finishedDateTime to always with 2 digits seconds
    private LocalDateTime startedDateTimeUTC;
    private LocalDateTime finishedDateTimeUTC;
    private Long durationSecs;
    private String fromStopId;
    private String toStopId;

    // TODO: Fix chargeAmount to a String with currency prefix and always 2 decimals
    private Double chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private TripStatus tripStatus;
}
