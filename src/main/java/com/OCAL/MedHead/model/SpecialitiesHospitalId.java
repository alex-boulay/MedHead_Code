package com.ocal.medhead.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SpecialitiesHospitalId implements Serializable {
	
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    
    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Specialities speciality;

    // Constructors, equals, and hashCode methods

    // Getters and setters
}