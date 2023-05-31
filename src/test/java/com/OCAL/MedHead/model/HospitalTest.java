package com.ocal.medhead.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HospitalTest {

    @Test
    public void testHospitalEntity() {
        // Création d'une instance Hospital
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setName("Hospital de Test");
        hospital.setAddress("2 place du Gal de Gaules, Lille, France");
        hospital.setLatitude(50.63657f); 
        hospital.setLongitude(3.06353f);

        // Vérification de la valeur des données 
        Assertions.assertEquals(1L, hospital.getId());
        Assertions.assertEquals("Hospital de Test", hospital.getName());
        Assertions.assertEquals("2 place du Gal de Gaules, Lille, France", hospital.getAddress());
        Assertions.assertEquals(50.63657f, hospital.getLatitude());
        Assertions.assertEquals(3.06353f, hospital.getLongitude());
        
        // Vérification de valeurs différentes 
        Assertions.assertNotEquals(2L, hospital.getId());
        Assertions.assertNotEquals("HospitalTest mais pas le bon", hospital.getName());
        Assertions.assertNotEquals("2 rue de la mauvaise addresse, Lille, France", hospital.getAddress());
        Assertions.assertNotEquals(41.123456f, hospital.getLatitude());
        Assertions.assertNotEquals(-72.987654f, hospital.getLongitude());
    }
}