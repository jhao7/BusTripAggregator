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

    private LocalDateTime startedDateTimeUTC;
    private LocalDateTime finishedDateTimeUTC;
    private Long durationSecs;
    private String fromStopId;
    private String toStopId;
    private String chargeAmount;
    private String companyId;
    private String busId;
    private String pan;
    private TripStatus tripStatus;
}
