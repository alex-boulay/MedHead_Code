package com.ocal.medhead.repository;

import com.ocal.medhead.model.SpecialitiesHospital;
import com.ocal.medhead.model.SpecialitiesHospitalId;
import com.ocal.medhead.model.Specialities;
import com.ocal.medhead.model.SpecGroup;
import com.ocal.medhead.model.Hospital;
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
public class SpecialitiesHospitalRepositoryTest {
	
	@Autowired 
	private SpecGroupRepository specGroupRepository;

	@Autowired
	private SpecialitiesRepository specialitiesRepository;
	
	@Autowired 
	private HospitalRepository hospitalRepository;
	
	@Autowired 
	private SpecialitiesHospitalRepository shr;
	
	@Test
	@Transactional
	public void testFindById(){
		//Création d'un hopital
		Hospital h = new Hospital();
		h.setAddress("2 rue du Test Lille");
		h.setLatitude(50.0f);
		h.setLongitude(3.0f);
		h.setName("Hopital de Test");
		h = hospitalRepository.save(h);
		Assertions.assertNotNull(h,"L'Hopital ne devrait pas être nul");
		
		//Création d'un Groupe de Spécialitées
		SpecGroup sgroup = new SpecGroup();
		sgroup.setName("Groupe de Tests");
		sgroup = specGroupRepository.save(sgroup);
		
		//Création de plusieurs Spécialitées
		Specialities spec = new Specialities();
		spec.setName("Spécialité de Test");
		spec.setSpecgroup(sgroup);
		spec = specialitiesRepository.save(spec);
		Specialities spec2 = new Specialities();
		spec2.setName("Spécialité de Test Secondaire");
		spec2.setSpecgroup(sgroup);
		spec2 = specialitiesRepository.save(spec2);
		
		//Lien entre plusieurs spécialitées et un Hopital
		SpecialitiesHospital spehos = new SpecialitiesHospital(h,spec);
		spehos = shr.save(spehos);
		
		//Test Création et Lecture "CreateRead"
		SpecialitiesHospital fspehos = shr.findById(spehos.getId()).orElse(null);
		Assertions.assertNotNull(fspehos,"L'élément créé n'a pas été trouvé !");
		Assertions.assertEquals(spehos.getHospital(),fspehos.getHospital(),"L'Hopital ne correspond pas");
		Assertions.assertEquals(spehos.getSpeciality(), fspehos.getSpeciality(),"La Spécialité ne correspond pas");
		//Test  de la méthode Update
		fspehos.setSpeciality(spec2);
		fspehos = shr.save(fspehos);
		shr.count();
		fspehos = shr.findById(fspehos.getId()).orElse(null);
		Assertions.assertNotNull(fspehos,"L'élément édité n'a pas été trouvé !");
		Assertions.assertEquals(fspehos.getSpeciality(), fspehos.getSpeciality(),"La Spécialité ne correspond pas après l'update");
		//Test de la méthode Delete
		shr.delete(fspehos);
		fspehos = shr.findById(spehos.getId()).orElse(null);
		Assertions.assertNull(fspehos,"Problème lors de la suppresion de l'élément");
		//Nettoyage
		specialitiesRepository.delete(spec2);
		specialitiesRepository.delete(spec);
		specGroupRepository.delete(sgroup);
		hospitalRepository.delete(h);
		
	}
	@Test
	@Transactional
	public void testFindBySpeciality_Id(){
		//Création de plusieurs hopitaux
		Hospital h1 = new Hospital();
		h1.setAddress("1 rue du Test Lille");
		h1.setLatitude(50.1f);
		h1.setLongitude(3.0f);
		h1.setName("Hopital de Test un");
		h1 = hospitalRepository.save(h1);
		Assertions.assertNotNull(h1,"L'Hopital ne devrait pas être nul");
		Hospital h2 = new Hospital();
		h2.setAddress("2 rue du Test Lille");
		h2.setLatitude(50.2f);
		h2.setLongitude(3.0f);
		h2.setName("Hopital de Test deux");
		h2 = hospitalRepository.save(h2);
		Assertions.assertNotNull(h2,"L'Hopital ne devrait pas être nul");
		Hospital h3 = new Hospital();
		h3.setAddress("3 rue du Test Lille");
		h3.setLatitude(50.3f);
		h3.setLongitude(3.0f);
		h3.setName("Hopital de Test trois");
		h3 = hospitalRepository.save(h3);
		Assertions.assertNotNull(h3,"L'Hopital ne devrait pas être nul");
		
		//Création d'un Groupe de Spécialitées
		SpecGroup sgroup = new SpecGroup();
		sgroup.setName("Groupe de Tests");
		sgroup = specGroupRepository.save(sgroup);
		
		//Création de plusieurs Spécialitées
		Specialities spec = new Specialities();
		spec.setName("Spécialité de Test");
		spec.setSpecgroup(sgroup);
		spec = specialitiesRepository.save(spec);
		
		//Lien entre une spécialité et plusieurs Hopitaux
		SpecialitiesHospital spehos1 = new SpecialitiesHospital(h1,spec);
		spehos1 = shr.save(spehos1);
		SpecialitiesHospital spehos2 = new SpecialitiesHospital(h2,spec);
		spehos2 = shr.save(spehos2);
		SpecialitiesHospital spehos3 = new SpecialitiesHospital(h3,spec);
		spehos3 = shr.save(spehos3);
		
		//Tests
		List<SpecialitiesHospital> lsp = shr.findById_Speciality(spec);
		Assertions.assertEquals(lsp.size(),3,"La liste ne contient pas autant d'éléments que ce qui à été implémenté");
		for(SpecialitiesHospital sh : lsp) {
			Assertions.assertEquals(sh.getSpeciality(), spec,"La spécialité est différente de celle renseignée");
		}
		Assertions.assertEquals(lsp.get(0).getHospital(),h1,"L'hopsital n°1 ne correspond pas");
		Assertions.assertEquals(lsp.get(1).getHospital(),h2,"L'hopsital n°2 ne correspond pas");
		Assertions.assertEquals(lsp.get(2).getHospital(),h3,"L'hopsital n°3 ne correspond pas");
		//Nettoyage
		shr.delete(spehos3);
		shr.delete(spehos2);
		shr.delete(spehos1);
		specialitiesRepository.delete(spec);
		specGroupRepository.delete(sgroup);
		hospitalRepository.delete(h3);
		hospitalRepository.delete(h2);
		hospitalRepository.delete(h1);
	}
	
}
