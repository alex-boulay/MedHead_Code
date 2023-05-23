package com.OCAL.MedHead.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "specialities_hospital")
public class SpecialitiesHospital {
    @EmbeddedId
    private SpecialitiesHospitalId id;

    @MapsId("hospitalId")
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @MapsId("specialityId")
    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Specialities speciality;

    // Getters and setters
}
