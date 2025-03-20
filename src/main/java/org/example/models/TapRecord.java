package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.models.enums.TapType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TapRecord {

    private Long id;
    private LocalDateTime tapDateTimeUTC;
    private TapType tapType;
    private String stopId;
    private String companyId;
    private String busId;
    private String pan;

    @Override
    public boolean equals(Object o) {
        if (o instanceof TapRecord) {
            TapRecord tapRecord = (TapRecord) o;
            return this.id.equals(tapRecord.id)
                    && this.tapDateTimeUTC.equals(tapRecord.tapDateTimeUTC)
                    && this.tapType.equals(tapRecord.tapType)
                    && this.stopId.equals(tapRecord.stopId)
                    && this.companyId.equals(tapRecord.companyId)
                    && this.busId.equals(tapRecord.busId)
                    && this.pan.equals(tapRecord.pan);
        }
        return super.equals(o);
    }
}
