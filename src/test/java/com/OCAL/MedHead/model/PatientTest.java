package com.ocal.medhead.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatientTest {

    @Test
    public void testPatientEntity() {
        // Création d'une Instance Patient
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Pierre Dupont");
        patient.setAddress("1 rue du Gal de Gaules");
        patient.setLatitude(42.987654f);
        patient.setLongitude(-71.123456f);

        // Vérification de l'intégritée des données
        Assertions.assertEquals(1L, patient.getId());
        Assertions.assertEquals("Pierre Dupont", patient.getName());
        Assertions.assertEquals("1 rue du Gal de Gaules", patient.getAddress());
        Assertions.assertEquals(42.987654f, patient.getLatitude());
        Assertions.assertEquals(-71.123456f, patient.getLongitude());
        
        //Vérification de valeurs différentes
        Assertions.assertNotEquals(2L, patient.getId());
        Assertions.assertNotEquals("George Dupont", patient.getName());
        Assertions.assertNotEquals("2 rue du Gal de Gaules", patient.getAddress());
        Assertions.assertNotEquals(41.987654f, patient.getLatitude());
        Assertions.assertNotEquals(-72.123456f, patient.getLongitude());
    }
}