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
public class SpecialitiesHospitalRepositoryTest {
	
	@Autowired 
	private SpecGroupRepository specGroupRepository;

	@Autowired
	private SpecialitiesRepository specialitiesRepository;
	
	@Test
	@Transactional
	public void testFindById(){
		
	}
	@Test
	@Transactional
	public void testFindBySpeciality_Id(){
		
	}
	
}
