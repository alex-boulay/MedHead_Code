package com.ocal.medhead.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import com.ocal.medhead.model.*;
import com.ocal.medhead.repository.*;

@Service
public class AvailableBedService {
	
	@Autowired 
	private BedRepository br;
	@Autowired 
	private BedOccupationRepository bor;
	
	public int availableBeds(Hospital h ) {
		int i = 0;
		for (Bed b : br.findByHospital(h)) {
			if (this.isAvailable(b)) {
				i++;
			}
		}
		return i;
	}
	
	public boolean isAvailable(Bed b) {
		boolean bedsavailable = true;
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		for(BedOccupation bo : bor.findByBed(b)) {
			if ( bo.getStart().before(date) && bo.getEnd().after(date)) {
				return false ;
			}
		}
		return bedsavailable;
	}
}
