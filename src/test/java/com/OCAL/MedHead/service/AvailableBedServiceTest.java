package com.ocal.medhead.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ocal.medhead.model.Bed;
import com.ocal.medhead.model.BedOccupation;
import com.ocal.medhead.model.BedOccupationId;
import com.ocal.medhead.model.Patient;
import com.ocal.medhead.repository.BedOccupationRepository;
import com.ocal.medhead.repository.BedRepository;
import com.ocal.medhead.repository.PatientRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AvailableBedServiceTest {

	@Autowired 
	private BedRepository br;
	@Autowired
	private BedOccupationRepository bor;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private AvailableBedService abs;
	
	// La fonction availableBeds est une boucle de availableBed
	// L'élément findByHospital à déja été testé dans BedRepository
	// Le test porte donc sur availablebed
	@Test
	@Transactional
	public void testAvailableBed() {
		//findByIdBed a déja ététesté dans BedOccupationRepository
		
		//Create bed
		Bed b =new Bed();
		b = br.save(b);
		Bed b2 =new Bed();
		b2 = br.save(b2);
		
		//Create Patient
		Patient p = new Patient();
		p.setName("Thierry Dubois");
		p.setAddress("3 rue de la Forêt, Lille");
		p = patientRepository.save(p);
		
		//Create Times
        LocalDate now = LocalDate.now();
		LocalDate ts = now.minusDays(10);
		LocalDate te = now.plusDays(10);
		BedOccupation bo = new BedOccupation();
		bo.setId(new BedOccupationId());
		bo.setBed(b);
		bo.setStart(ts);
		bo.setEnd(te);
		bo.setPatient(p);
		bo=bor.save(bo);
		
		Assertions.assertFalse(abs.isAvailable(b));
		Assertions.assertTrue(abs.isAvailable(b2));
	}
	
}
