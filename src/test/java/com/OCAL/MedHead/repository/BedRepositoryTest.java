package com.ocal.medhead.repository;


import com.ocal.medhead.model.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.ocal.medhead.model.Hospital;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BedRepositoryTest {
	
	@Autowired 
	private BedRepository bedRepository;	
	
	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Test
	@Transactional
	public void testFindById(){
		Hospital h = hospitalRepository.findById(1L).orElse(null);
		Bed bed = new Bed();
		bed.setHospital(h);
		bed = bedRepository.save(bed);
		
		Bed retrievedBed = bedRepository.findById(bed.getId()).orElse(null);
		//Tests de validation des données
		Assertions.assertEquals(bed.getId(),retrievedBed.getId(),"L'ID du bed créé et de celui sauvegardé doivent correspondre.");
		Assertions.assertEquals(bed.getHospital(),retrievedBed.getHospital(),"L'Hospital du bed créé et de celui sauvegardé doivent correspondre.");
		// Test de validation de données érronnées
		Assertions.assertNotEquals(2L,retrievedBed.getId(),"L'id ne doit pas correspondre a celui généré");
		Assertions.assertNotEquals(2L,retrievedBed.getId(),"L'HospitalID ne doit pas correspondre à celui généré");	
	}
	
	@Test
	@Transactional
	public void testFindByHospital(){
		Hospital h2 = hospitalRepository.findById(1L).orElse(null);
		List<Bed> bedlist = bedRepository.findByHospital(h2);
		int i = 1;
		for(Bed bed : bedlist) {
			Assertions.assertEquals(bed.getId(),i,"Beds much match the first IDs for the first hospital");
			Assertions.assertEquals(bed.getHospital(),h2,"Beds much match the selected Hospital");
			Assertions.assertTrue(bed.getHospital().getId()< 2L,"Hospital IDs must not be different than 1");
			i++;
		}
		
	}
}
