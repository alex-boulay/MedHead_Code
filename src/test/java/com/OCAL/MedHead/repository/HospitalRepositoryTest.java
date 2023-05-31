package com.ocal.medhead.repository;

import com.ocal.medhead.model.*;
import static org.junit.Assert.*;
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
public class HospitalRepositoryTest {
	
	@Autowired 
	private HospitalRepository hospitalRepository;
	
	@Test
	@Transactional
	public void testFindById() {
		// Déclaration d'un hopital
		Hospital hosp = new Hospital("StTest Hosp","23 Gal de Gaule, Lille, France",50.0f,3.00f);
		
		// Sauvegarde en BDD
		hosp= hospitalRepository.save(hosp);
		
		 // Récupération de l'hôpital à partir du repository
        Hospital retrievedHospital = hospitalRepository.findById(hosp.getId()).orElse(null);

        // Vérification que l'hôpital récupéré est identique à l'hôpital enregistré
        Assertions.assertNotNull(retrievedHospital, "L'hôpital récupéré ne devrait pas être null.");
        Assertions.assertEquals(1327L, retrievedHospital.getId(), "Les IDs d'hôpital devraient être identiques.");
        Assertions.assertEquals(hosp.getAddress(), retrievedHospital.getAddress(), "Les addresses des hôpitaux devraient être identiques.");
        Assertions.assertEquals(hosp.getName(), retrievedHospital.getName(), "Les noms d'hôpital devraient être identiques.");
        Assertions.assertEquals(hosp.getLatitude(), retrievedHospital.getLatitude(), "Les coordonnées d'hôpital devraient être identiques.");
        Assertions.assertEquals(hosp.getLongitude(), retrievedHospital.getLongitude(), "Les coordonnées d'hôpital devraient être identiques.");

        // Vérification sur des données différentes que les hopitaux sont bien différents 
        Assertions.assertNotEquals(49486L, retrievedHospital.getId(), "Les IDs d'hôpital devraient être différents.");
        Assertions.assertNotEquals("Saint Andrews", retrievedHospital.getName(), "Les noms d'hôpital devraient être différents.");
        Assertions.assertNotEquals(51.01f, retrievedHospital.getLatitude(), "Les coordonnées d'hôpital devraient être différents.");
        Assertions.assertNotEquals(3.04f, retrievedHospital.getLongitude(), "Les coordonnées d'hôpital devraient être différents.");

        hospitalRepository.delete(hosp);
        
        // vérfication sur des données existantes
        retrievedHospital = hospitalRepository.findById(1L).orElse(null);
        Assertions.assertEquals(1L, retrievedHospital.getId(), "Les IDs d'hôpital devraient être identiques");
        Assertions.assertEquals("KT12 3LD Rodney Road  Walton-on-Thames Surrey ", retrievedHospital.getAddress(), "Les addresses des hôpitaux devraient être identiques.");
        Assertions.assertEquals("Walton Community Hospital - Virgin Care Services Ltd", retrievedHospital.getName(), "Les noms d'hôpital devraient être identiques.");
        Assertions.assertEquals(51.3799972534f, retrievedHospital.getLatitude(), "Les coordonnées d'hôpital devraient être identiques.");
        Assertions.assertEquals(-0.4060420692f, retrievedHospital.getLongitude(), "Les coordonnées d'hôpital devraient être identiques.");
	}

}
