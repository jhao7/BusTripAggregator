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
}
