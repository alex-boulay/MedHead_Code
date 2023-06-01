package com.ocal.medhead.repository;

import com.ocal.medhead.model.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import jakarta.transaction.Transactional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PatientRepositoryTest {
	@Autowired 
	private PatientRepository patientRepository;

	@Test
	@Transactional
	public void testFindById_Bed(){
		// Patient data
		Patient patient = new Patient();
		patient.setAddress("place du Gal de Gaule, Lille,France");
		patient.setName("Paul Bocuse");
		patient.setLatitude(50.0f);
		patient.setLongitude(3.0f); 
	
		patient = patientRepository.save(patient);
		Patient fetchedPatient = patientRepository.findById(patient.getId()).orElse(null);
		//Assertions sur des données vraies
		Assertions.assertNotNull(fetchedPatient);
		Assertions.assertEquals(fetchedPatient.getId(),patient.getId(),"Les IDs du patient ne correspondent pas");
		Assertions.assertEquals(fetchedPatient.getAddress(),"place du Gal de Gaule, Lille,France","Les Addresse du patient ne correspondent pas");
		Assertions.assertEquals(fetchedPatient.getName(),"Paul Bocuse" ,"Le nom du patient ne correspond pas");
		Assertions.assertEquals(fetchedPatient.getLatitude(),50.0f,"La Latitude du patient ne correspond pas");
		Assertions.assertEquals(fetchedPatient.getLongitude(), 3.0f,"La Longitude du patient ne correspond pas");
		
		//Assertions sur des données fausses
		Assertions.assertNotEquals(fetchedPatient.getAddress(),"rue du Gal de Gaules, Lille,France","L'addresse du patient doit ne pas correspondre");
		Assertions.assertNotEquals(fetchedPatient.getName(),"Jacques Bocuse", "Le Nom du Patient doit ne pas correspondre");
		Assertions.assertNotEquals(fetchedPatient.getLatitude(),47.0f, "La Latitude du patient ne doit pas correspondre");
		Assertions.assertNotEquals(fetchedPatient.getLongitude(),3.1f, "La Longitude du patient ne doit pas correspondre");
		
		patientRepository.delete(patient);
	}
}
