package com.ocal.medhead.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BedTest {

    @Test
    public void testBedEntity() {
        // Create a Bed instance
        Bed bed = new Bed();

        // Données a tester
        Long id = 1L;
        Hospital hospital = new Hospital();
        hospital.setName("Hopital numéro 1");
        Hospital hospital2 = new Hospital();
        hospital2.setName("Hopital numéro 2");
        bed.setId(id);
        bed.setHospital(hospital);

        // Vérification de l'intégritée des données
        Assertions.assertEquals(id, bed.getId());
        Assertions.assertEquals(hospital, bed.getHospital());
        
        // Vérification de données différentes
        Assertions.assertNotEquals(2L, bed.getId());
        Assertions.assertNotEquals(hospital2, bed.getHospital());
    }
}