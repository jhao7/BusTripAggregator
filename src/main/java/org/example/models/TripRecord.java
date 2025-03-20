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

    @Override
    public boolean equals(Object o) {
        if (o instanceof TripRecord) {
            TripRecord tripRecord = (TripRecord) o;
            return this.startedDateTimeUTC.equals(tripRecord.startedDateTimeUTC)
                    && ((this.finishedDateTimeUTC == null || tripRecord.finishedDateTimeUTC == null)
                                    ? true : this.finishedDateTimeUTC.equals(tripRecord.finishedDateTimeUTC))
                    && ((this.durationSecs == null || tripRecord.durationSecs == null)
                                    ? true : this.durationSecs.equals(tripRecord.durationSecs))
                    && this.fromStopId.equals(tripRecord.fromStopId)
                    && ((this.toStopId == null || tripRecord.toStopId == null)
                                    ? true : this.toStopId.equals(tripRecord.toStopId))
                    && this.chargeAmount.equals(tripRecord.chargeAmount)
                    && this.companyId.equals(tripRecord.companyId)
                    && this.busId.equals(tripRecord.busId)
                    && this.pan.equals(tripRecord.pan)
                    && this.tripStatus.equals(tripRecord.tripStatus);
        }
        return super.equals(o);
    }
}
