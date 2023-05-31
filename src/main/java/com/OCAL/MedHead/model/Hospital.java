package com.ocal.medhead.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "hospital")
@Data
public class Hospital {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;
    
    @Column(name="address")
    private String address;
    
    @Column(name="latitude")
    private float latitude;
    
    @Column(name="longitude")    
    private float longitude; 
    
    public Hospital() {
    	
    }
    
    public Hospital(String name,String address,float lat,float lon) {
    	this.name = name;
    	this.address = address;
    	this.latitude = lat;
    	this.longitude = lon;
    }
}
