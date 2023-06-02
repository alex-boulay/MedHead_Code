package com.ocal.medhead.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "specialities_hospital")
public class SpecialitiesHospital {
    @EmbeddedId
    private SpecialitiesHospitalId id;

    public Hospital getHospital() {
    	return id.getHospital();
    }
    public Specialities getSpeciality() {
    	return id.getSpeciality();
    }
    public void setSpeciality(Specialities spec) {
    	id.setSpeciality(spec);
    }
    public void setHospital(Hospital h) {
    	id.setHospital(h);
    }
    public SpecialitiesHospital(){
    	this.id= new SpecialitiesHospitalId();
    }
    public SpecialitiesHospital(Hospital h,Specialities s){
    	this.id= new SpecialitiesHospitalId();
    	setHospital(h);
    	setSpeciality(s);
    }

}
