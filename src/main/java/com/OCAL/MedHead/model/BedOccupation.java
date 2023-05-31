package com.ocal.medhead.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bed_occupation")
public class BedOccupation {
	@EmbeddedId 
	private BedOccupationId id;

    @ManyToOne
    @JoinColumn(name="bed_id", insertable = false, updatable = false)
    private Bed bed;

    @ManyToOne
    @JoinColumn(name="patient_id", insertable = false, updatable = false)
    private Patient patient;

    @Column(name="start_date", insertable = false, updatable = false)
    private LocalDate start;
    
    @Column(name="end_date", insertable = false, updatable = false)
    private LocalDate end;

}
