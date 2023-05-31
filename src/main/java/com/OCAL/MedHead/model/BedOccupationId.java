package com.ocal.medhead.model;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
public class BedOccupationId implements Serializable {
    @Column(name = "bed_id")
    private Long bedId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "start_date")
    private LocalDate startDate;

}
