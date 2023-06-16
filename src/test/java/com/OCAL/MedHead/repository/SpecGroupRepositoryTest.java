package com.ocal.medhead.repository;

import com.ocal.medhead.model.SpecGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SpecGroupRepositoryTest {
	
	@Autowired 
	private SpecGroupRepository specGroupRepository;

	@Test
	@Transactional
	public void testFindById(){
		SpecGroup specGroup = new SpecGroup();
		specGroup.setName("Médecine de Test");
		specGroup = specGroupRepository.save(specGroup);
	
		SpecGroup fsg= specGroupRepository.findById(specGroup.getId()).orElse(null); // fsg pour fetchedSpecGroup
		//test sur Create et Read de CRUD
		Assertions.assertNotNull(specGroup,"Le Groupe de spécialité ajouté n'a pas pu être trouvé");
		Assertions.assertEquals(fsg.getName(),"Médecine de Test","Les Noms de Groupe de spécialité ne corresponde pas suite a la création");
		//test sur la fonction update de CRUD
		specGroup.setName("Médecine de reTest");
		specGroup = specGroupRepository.save(specGroup);
		fsg = specGroupRepository.findById(specGroup.getId()).orElse(null); 
		Assertions.assertEquals(fsg.getName(),"Médecine de reTest","Les Noms de Groupe de spécialité ne corresponde pas suite a l'update");
		//Test sur la fonction Delete
		specGroupRepository.delete(fsg);
		Assertions.assertNull(specGroupRepository.findById(specGroup.getId()).orElse(null)," L'élément retourné n'est pas nul");
	}
}