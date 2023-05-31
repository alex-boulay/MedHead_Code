package com.ocal.medhead.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpecialitiesTest {

    @Test
    public void testSpecialitiesEntity() {
        // Création d'une instance de Specialities
        Specialities specialities = new Specialities();
        specialities.setId(1L);

        // Création d'une instance de SpecGroup
        SpecGroup specGroup = new SpecGroup();
        specGroup.setName("Médecine de Test");

        // Définition de la relation SpecGroup
        specialities.setSpecgroup(specGroup);

        // Définition du nom de la spécialité
        specialities.setName("Exemple de spécialité");

        // Vérification des valeurs définies
        Assertions.assertEquals(1L, specialities.getId(), "L'ID de la spécialité ne correspond pas.");
        Assertions.assertEquals(specGroup, specialities.getSpecgroup(), "Le groupe de spécifications de la spécialité ne correspond pas.");
        Assertions.assertEquals("Exemple de spécialité", specialities.getName(), "Le nom de la spécialité ne correspond pas.");
        
        // Vérification que les valeurs ne sont pas égales à d'autres valeurs
        Assertions.assertNotEquals(2L, specialities.getId(), "L'ID de la spécialité ne devrait pas être égal à 2.");
        Assertions.assertNotEquals(new SpecGroup(), specialities.getSpecgroup(), "Le groupe de spécifications de la spécialité ne devrait pas être égal à un nouvel objet SpecGroup.");
        Assertions.assertNotEquals("Autre spécialité", specialities.getName(), "Le nom de la spécialité ne devrait pas être égal à 'Autre spécialité'.");
    }
}