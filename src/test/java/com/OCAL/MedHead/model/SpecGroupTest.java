package com.ocal.medhead.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpecGroupTest {

    @Test
    public void testSpecGroupEntity() {
        // Création d'une instance de SpecGroup
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(1L);
        specGroup.setName("Exemple de groupe de spécifications");

        // Vérification des valeurs définies
        Assertions.assertEquals(1L, specGroup.getId(), "L'ID du groupe de spécifications ne correspond pas.");
        Assertions.assertEquals("Exemple de groupe de spécifications", specGroup.getName(), "Le nom du groupe de spécifications ne correspond pas.");

        // Vérification de valeurs différentes
        Assertions.assertNotEquals(2L, specGroup.getId(), "L'ID du groupe de spécifications ne devrait pas être égal à 2.");
        Assertions.assertNotEquals("Autre groupe de spécifications", specGroup.getName(), "Le nom du groupe de spécifications ne devrait pas être égal à 'Autre groupe de spécifications'.");
	}
}