package com.ocal.medhead.service;


import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
	@Test
	@Transactional
	public void testCoordinateFromAddress() {
		String address1 = "piccadilly circus london";
		Coordinates c1 = new Coordinates(-0.133999464f,51.505997976f);
		Coordinates c2 ;
		Coordinates c4;
		try{
			c2= bfs.getCoordinatesFromAddress(address1);
			Assertions.assertTrue(c1.compareCoord(c2),"Les Coordonnées sont trop éloignées");
		
			String address2 = "Victoria Square, Birmingham, England";
			Coordinates c3 = new Coordinates(-1.902765f, 52.479764f);
			try{
				c4 = bfs.getCoordinatesFromAddress(address2);
				Assertions.assertTrue(c3.compareCoord(c4),"Les Coordonnées sont trop éloignées");
				Assertions.assertFalse(c2.compareCoord(c4),"Les Coordonnées ne doivent pas être proche");
			}
			catch(Exception e) {
				Assertions.assertFalse(true,"Parsing error lors du Test");
			}
		}
		catch(Exception e) {
			Assertions.assertFalse(true,"Parsing error lors du Test");
		}
	}
	@Test
	@Transactional
	public void testGetClosestHospital() {
		Coordinates c1 = new Coordinates();
		c1.setLatitude(51.505997976f);
		c1.setLongitude(-0.133999464f);
		Hospital h1 = new Hospital();
		h1.setLatitude(49.0f);
		h1.setLongitude(-1.0f);
		h1 = hospitalRepository.save(h1);
		Hospital h2 = new Hospital();
		h2.setLatitude(49.5f);
		h2.setLongitude(-0.5f);
		h2 = hospitalRepository.save(h2);
		Hospital h3 = new Hospital();
		h3.setLatitude(50.0f);
		h3.setLongitude(0.0f);
		h3 = hospitalRepository.save(h3);
		HospitalDTO hdto1= new HospitalDTO(0.f,h1);
		HospitalDTO hdto2= new HospitalDTO(0.f,h2);
		HospitalDTO hdto3= new HospitalDTO(0.f,h3);
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
		
	}
}
