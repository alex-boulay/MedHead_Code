package com.OCAL.MedHead.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "bed_occupation")
public class BedOccupation {

    @Column(name="bed_id")
    @Id
    private Long bed_id;

    @Column(name="hospital_id")
    private Long hospital_id;
    
    @Column(name="start_date")
    private java.util.Date start_date;
    
    @Column(name="end_date")
    private java.util.Date end_date;

}
