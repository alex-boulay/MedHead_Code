package com.ocal.medhead.service;


import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.ocal.medhead.model.Hospital;
import com.ocal.medhead.repository.HospitalRepository;

import dto.*;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BedFindingServiceTest {
	@Autowired 
	private BedFindingService bfs;
	
	@Autowired 
	private HospitalRepository hospitalRepository;
	
	// In this test elements like External server error cannot be tested
	@Test
	@Transactional
	public void testCoordinateFromAddress() {
		String address1 = "piccadilly circus london";
		Coordinates c1 = new Coordinates(51.505997976f,-0.133999464f);
		Coordinates c2 = new Coordinates();
		Coordinates c4 ;
		try{
			c2= bfs.getCoordinatesFromAddress(address1);
			Assertions.assertTrue(c1.compareCoord(c2),"Les Coordonnées sont trop éloignées");
		}
		catch(Exception e) {
			Assertions.assertFalse(true,"Parsing error lors du Test"+e);
		}		
		String address2 = "Victoria Square, Birmingham, England";
		Coordinates c3 = new Coordinates(52.479764f,-1.902765f);
		try{
			c4 = bfs.getCoordinatesFromAddress(address2);
			Assertions.assertTrue(c3.compareCoord(c4),"Les Coordonnées sont trop éloignées");
			Assertions.assertFalse(c2.compareCoord(c4),"Les Coordonnées ne doivent pas être proche");
		}
		catch(Exception e) {
			Assertions.assertFalse(true,"Parsing error lors du Test" + e);
		}

		String address3 = "obafezofbalfarbgrrgr";
		try {
			bfs.getCoordinatesFromAddress(address3);
			Assertions.assertFalse(true, "This function should have resolved in an Exception");
		}
		catch(Exception e) {
		}
	}
	
	
	
	@Test
	@Transactional
	public void testGetClosestHospital() {
		Coordinates c1 = new Coordinates();
		c1.setLatitude(51.505997976f);
		c1.setLongitude(-0.133999464f);
		Hospital h1 = new Hospital();// Birmingham 
		h1.setLatitude(52.450988f);
		h1.setLongitude(-1.861189f);
		h1 = hospitalRepository.save(h1);
		Hospital h2 = new Hospital();//Cambridge
		h2.setLatitude(52.177721f);
		h2.setLongitude(0.117290f);
		h2 = hospitalRepository.save(h2);
		Hospital h3 = new Hospital(); // Chelmsford
		h3.setLatitude(51.722349f);
		h3.setLongitude(0.507452f);
		h3 = hospitalRepository.save(h3);
		HospitalDTO hdto1= new HospitalDTO(0.f,h1,4);
		HospitalDTO hdto2= new HospitalDTO(0.f,h2,4);
		HospitalDTO hdto3= new HospitalDTO(0.f,h3,4);
		List<HospitalDTO> lhdto = new LinkedList<>();
		lhdto.add(hdto1);
		lhdto.add(hdto2);
		lhdto.add(hdto3);
		try {
			List<HospitalDTO> lhdto_sorted = bfs.getClosestHospital(c1, lhdto);
			//verification sur l'ordre des éléments
			Assertions.assertTrue(lhdto_sorted.get(0).getDistance()<lhdto_sorted.get(1).getDistance(),"Distance matches order");
			Assertions.assertTrue(lhdto_sorted.get(1).getDistance()<lhdto_sorted.get(2).getDistance(),"Distance matches order");
			Assertions.assertEquals(lhdto_sorted.get(0).getHospital(),h3,"Third Hospital Should be the clossest");
			Assertions.assertEquals(lhdto_sorted.get(2).getHospital(),h1,"First Hospital Should be the furthest");
		}
		catch(Exception e) {
			Assertions.assertFalse(true,"Parse Excpetion not handled properly");
		}
		Coordinates c2 = new Coordinates();
		c2.setLatitude(-999.0f);
		c2.setLongitude(-999.0f);
		try {
			bfs.getClosestHospital(c2, lhdto);
			Assertions.assertFalse(true,"Did not Catch the Coordinates Issues");
		}
		catch(Exception e) {
			if ( !(e instanceof UnirestException)) {
				Assertions.assertFalse(true,"Wrong Exception handled");
			}
		}
		hospitalRepository.delete(h3);
		hospitalRepository.delete(h2);
		hospitalRepository.delete(h1);
	}
	
	@Test
	public void test_distance() {
		Coordinates c1 = new Coordinates(10.f,10.f);
		Coordinates c2 = new Coordinates(5.0f,5.0f);
		Hospital h1 = new Hospital();
		h1.setLatitude(10.f);
		h1.setLongitude(10.f);
		Assertions.assertEquals(0.f, bfs._distance(c1,h1));
		Assertions.assertEquals(50.f, bfs._distance(c2,h1));
	}
	
	@Test
	@Transactional
	public void testGetHospitalsBySpecS() {
		Coordinates c1 = new Coordinates(50.0f, 0.0f);
		List<HospitalDTO> lhdto = bfs.getHospitalsBySpecS(c1, 1);
		Assertions.assertEquals(lhdto.size(),10,"Get Hospital by spec should contain at least 10 elements for the spec 1");
		float curentdistance = 0f;
		for(HospitalDTO hdto : lhdto) {
			Assertions.assertTrue(curentdistance < hdto.getDistance(),"Distances are not sorted !");
			Assertions.assertTrue(hdto.getAvailableBed()>0,"Hospital should have availiable beds");
			curentdistance = hdto.getDistance();
			//the Hospital matching the given Spec is already tested in specialitieshospitalrepositoryTest
		}
	}
	
}
