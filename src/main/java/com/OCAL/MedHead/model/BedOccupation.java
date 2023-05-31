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
    
    @Column(name="end_date")
    private LocalDate end;

    public LocalDate getStart() {
    	return id.getStart();
    }
    
    public Patient getPatient() {
    	return id.getPatient();
    }
    public Bed getBed() {
    	return id.getBed();
    }
    public void setStart(LocalDate start) {
    	id.setStart(start);
    }
    public void setPatient(Patient patient) {
    	id.setPatient(patient);
    }
    public void setBed(Bed bed) {
    	id.setBed(bed);
    }
}
