package com.ocal.medhead.model;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class BedOccupationId implements Serializable {
    @ManyToOne
	@JoinColumn(name = "bed_id")
    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @JoinColumn(name = "start_date")
    private LocalDate start;

}
