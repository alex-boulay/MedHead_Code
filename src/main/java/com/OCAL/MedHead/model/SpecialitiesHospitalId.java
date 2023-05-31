package com.ocal.medhead.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SpecialitiesHospitalId implements Serializable {
    private Long hospitalId;
    private Long specialityId;

    // Constructors, equals, and hashCode methods

    // Getters and setters
}