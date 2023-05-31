package com.ocal.medhead.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BedOccupationTest {

    @Test
    public void testBedOccupationEntity() {
        // Ajout d'un BedoccupationId
        BedOccupationId bedOccupationId = new BedOccupationId();
        bedOccupationId.setBedId(1L);
        bedOccupationId.setPatientId(1L);

        // Ajout d'une instance Bed
        Bed bed = new Bed();
        bed.setId(1L);

        // Ajout d'une instance Patient
        Patient patient = new Patient();
        patient.setId(1L);

        // Création d'instances de Date
        LocalDate startDate = LocalDate.of(2023, 8, 1);
        LocalDate endDate = LocalDate.of(2023, 8, 4);

        // Création d'une bedOccupation
        BedOccupation bedOccupation = new BedOccupation();
        bedOccupation.setId(bedOccupationId);
        bedOccupation.setBed(bed);
        bedOccupation.setPatient(patient);
        bedOccupation.setStart(startDate);
        bedOccupation.setEnd(endDate);

        // Vérification de l'intégrité des données
        Assertions.assertEquals(bedOccupationId, bedOccupation.getId());
        Assertions.assertEquals(bed, bedOccupation.getBed());
        Assertions.assertEquals(patient, bedOccupation.getPatient());
        Assertions.assertEquals(startDate, bedOccupation.getStart());
        Assertions.assertEquals(endDate, bedOccupation.getEnd());
    }
}