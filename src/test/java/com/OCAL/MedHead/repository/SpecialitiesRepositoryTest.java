package com.ocal.medhead.repository;

import com.ocal.medhead.model.Specialities;
import com.ocal.medhead.model.SpecGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SpecialitiesRepositoryTest {
	
	@Autowired 
	private SpecGroupRepository specGroupRepository;

	@Autowired
	private SpecialitiesRepository specialitiesRepository;
	
	@Test
	@Transactional
	public void testFindById(){
		// Déploiement des différents éléments de test
		SpecGroup specg = new SpecGroup();
		specg.setName("Médecine de Test");
		specg = specGroupRepository.save(specg);
		Specialities spec = new Specialities();
		spec.setName("Spécialité de Test");
		spec.setSpecgroup(specg);
		spec = specialitiesRepository.save(spec);
		// Test de création et test de Read sur les valeurs
		Specialities fspec = specialitiesRepository.findById(spec.getId()).orElse(null);
		Assertions.assertNotNull(fspec,"La spécialité recherchée n'a pas été inscrite en base de donnée, erreur dans le save");
		Assertions.assertEquals(fspec.getName(),"Spécialité de Test","Les deux noms de spécialité ne correspondent pas");
		Assertions.assertEquals(fspec.getSpecgroup(),specg,"Les Groupes de spécialité ne correspondent pas");
		// Test de l'update
		spec.setName("Spécialité de reTest");
		spec = specialitiesRepository.save(spec);
		fspec = specialitiesRepository.findById(spec.getId()).orElse(null);
		Assertions.assertNotNull(fspec,"La spécialité recherchée n'a pas été inscrite en base de donnée suite à l'update");
		Assertions.assertEquals(fspec.getName(),"Spécialité de reTest","Les deux noms de spécialité ne correspondent pas");
		// Test de Suppression
		specialitiesRepository.delete(spec);
		fspec = specialitiesRepository.findById(spec.getId()).orElse(null);
		Assertions.assertNull(fspec,"La spécialité n'a pas été détruite suite à l'opération delete");
		specGroupRepository.delete(specg);
	}
	@Test
	@Transactional
	public void testFindBySpecgroup_Id() {
		// Setting a specgroup
		SpecGroup specg = new SpecGroup();
		specg.setName("Médecine de Test");
		specg = specGroupRepository.save(specg);
		// Setting differents specialities
		Specialities spec = new Specialities();
		spec.setName("Spécialité de Test");
		spec.setSpecgroup(specg);
		spec = specialitiesRepository.save(spec);
		Specialities spec2 = new Specialities();
		spec2.setName("Spécialité de Test Secondaire");
		spec2.setSpecgroup(specg);
		spec2 = specialitiesRepository.save(spec2);
		// Fetching the Elements
		SpecGroup fsg = specGroupRepository.findById(specg.getId()).orElse(null);
		Assertions.assertNotNull(fsg,"Le Groupe de Spécialité n'a pas été trouvé");
		List<Specialities> flistspec= specialitiesRepository.findBySpecgroup_Id(fsg.getId());
		Assertions.assertEquals(2,flistspec.size(),"La Liste de spécialité ne contient pas les deux éléments ajoutés");
		for (Specialities fspec : flistspec) {
			Assertions.assertEquals(specg,fspec.getSpecgroup(),"Le Groupe de Spécialité ne correspond pas.");
		}
		Assertions.assertEquals(flistspec.get(0).getName(),"Spécialité de Test","Le nom de la spécialité ne correspond pas");
		Assertions.assertEquals(flistspec.get(1).getName(),"Spécialité de Test Secondaire","Le nom de la spécialité ne correspond pas(2)");
		//Deleting one element
		specialitiesRepository.delete(spec2);
		flistspec= specialitiesRepository.findBySpecgroup_Id(fsg.getId());
		Assertions.assertEquals(1,flistspec.size(),"La Liste de spécialité ne contient pas l'élément");
		Assertions.assertEquals(flistspec.get(0).getName(),"Spécialité de Test","Le nom de la spécialité ne correspond pas");
		// Deleting last element
		specialitiesRepository.delete(spec);
		flistspec= specialitiesRepository.findBySpecgroup_Id(fsg.getId());
		Assertions.assertEquals(0,flistspec.size(),"La Liste de spécialité n'est pas vide");
		specGroupRepository.delete(specg);
	}
}
