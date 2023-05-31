package com.ocal.medhead.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpecialitiesHospitalTest {

    @Test
    public void testSpecialitiesHospitalEntity() {
        // Création d'une instance de SpecialitiesHospitalId
        SpecialitiesHospitalId id = new SpecialitiesHospitalId();

        // Création d'une instance de Hospital
        Hospital hospital = new Hospital();
        hospital.setId(1L);

        // Création d'une instance de Specialities
        Specialities speciality = new Specialities();
        speciality.setId(2L);

        // Création d'une instance de SpecialitiesHospital
        SpecialitiesHospital specialitiesHospital = new SpecialitiesHospital();
        specialitiesHospital.setId(id);
        specialitiesHospital.setHospital(hospital);
        specialitiesHospital.setSpeciality(speciality);

        // Vérification des valeurs définies
        Assertions.assertEquals(id, specialitiesHospital.getId(), "L'ID de SpecialitiesHospital ne correspond pas.");
        Assertions.assertEquals(hospital, specialitiesHospital.getHospital(), "L'hôpital de SpecialitiesHospital ne correspond pas.");
        Assertions.assertEquals(speciality, specialitiesHospital.getSpeciality(), "La spécialité de SpecialitiesHospital ne correspond pas.");
    }
}