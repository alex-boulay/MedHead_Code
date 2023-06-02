package com.ocal.medhead.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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
		for(BedOccupation bo : bor.findById_Bed(b)) {
			if ( bo.getStart().isBefore(localDate) && bo.getEnd().isAfter(localDate)) {
				return false ;
			}
		}
		return bedsavailable;
	}
}
