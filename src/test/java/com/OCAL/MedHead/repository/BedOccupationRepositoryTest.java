package com.ocal.medhead.repository;


import com.ocal.medhead.model.*;

import java.util.List;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BedOccupationRepositoryTest {
	
	@Autowired 
	private BedRepository bedRepository;	
	
	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Autowired 
	private PatientRepository patientRepository;
	
	@Autowired 
	private BedOccupationRepository bedOccupationRepository;
	
	@Test
	@Transactional
	public void testFindByBed(){
		// Patient data
		Patient patient = new Patient();
		patient.setAddress("place du Gal de Gaule, Lille,France");
		patient.setName("Paul Bocuse");
		patient.setLatitude(50.0f);
		patient.setLongitude(3.0f);
		patient = patientRepository.save(patient);
		
		//Bed data
		Bed bed = bedRepository.findById(1L).orElse(null);
		
		//BedOccupation data
		BedOccupation bedO = new BedOccupation();
		bedO.setBed(bed);
		bedO.setPatient(patient);
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 4);
		bedO.setStart(startDate);
		bedO.setEnd(endDate);
		
		// Test de la création
		bedO = bedOccupationRepository.save(bedO);
		
		// Comparaison des valeurs
		List<BedOccupation> lbo = bedOccupationRepository.findByBed(bed);
		Assertions.assertEquals(lbo.size(),1,"Occupation list should held only one item for given bed");
		Assertions.assertEquals(lbo.get(0).getBed(),bed,"Beds are instanciated from the same origin they should be the same");
		Assertions.assertEquals(lbo.get(0).getPatient(),patient,"Patients are instanciated from the same origin they should be the same");
		Assertions.assertEquals(lbo.get(0).getStart(),startDate,"Start Date are instanciated from the same origin they should be the same");
		Assertions.assertEquals(lbo.get(0).getEnd(),endDate,"End Date are instanciated from the same origin they should be the same");
		
	}
	@Test
	@Transactional 
	public void testFindByBed() {
		
	}
}
