package com.ocal.medhead.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
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