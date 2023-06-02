package com.ocal.medhead.service;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ocal.medhead.model.Hospital;
import com.ocal.medhead.model.Specialities;
import com.ocal.medhead.model.SpecialitiesHospital;
import com.ocal.medhead.repository.*;

import dto.Coordinates;
import dto.HospitalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import jakarta.transaction.Transactional;

@Data
@Service
public class BedFindingService {
	
	@Autowired
	private HospitalRepository HospitalRepository;
	@Autowired
	private SpecialitiesRepository SpecialitiesRepository;
	@Autowired
	private SpecialitiesHospitalRepository SpecialitiesHospitalRepository;
	@Autowired 
	private AvailableBedService Abs;

	public List<SpecialitiesHospital> getHospitalsBySpec(Long spec_id){
		return SpecialitiesHospitalRepository.findBySpeciality_Id(spec_id);
	}
	public List<Specialities> getSpecialitiesByGroupId(Long groupid){
		return SpecialitiesRepository.findBySpecgroup_Id(groupid);
	}
	public Coordinates getCoordinatesFromAddress(String address) throws ParseException {
		String uri = "http://nominatim.openstreetmap.org/search?q=";
		uri += address.replaceAll(" ","+");
		uri += "&format=json";

		float latitude = -999.0f;
		float longitude = -999.0f;
		
		HttpResponse<JsonNode> response;
		try {
			response = Unirest.get(uri).asJson();
			JSONParser parser = new JSONParser();
			String content =response.getBody().toString();
			Object ob = parser.parse(content);
			JSONArray js =(JSONArray) ob;
			JSONObject job =(JSONObject) js.get(0);
		    latitude = Float.parseFloat(job.get("lat").toString());
		    longitude = Float.parseFloat(job.get("lon").toString());
		} catch (UnirestException e) {

			System.out.println("Error 1"+e.toString());
			e.printStackTrace();
		}
		
	    return new Coordinates(longitude,latitude);
	}
	public List<HospitalDTO> getClosestHospital(Coordinates c,List<HospitalDTO> hospitals) throws ParseException {
		
		// Parsing the Data to match OSRM API
		String uri = "http://router.project-osrm.org/table/v1/driving/";
		uri += c.latLong()+";";
		for (HospitalDTO h : hospitals) {
			uri+= Float.toString(h.getHospital().getLongitude())+","+Float.toString( h.getHospital().getLatitude())+";";
		}
		uri = uri.substring(0, uri.length() - 1);
		uri += "?sources=0&destinations=";
		int i = 1;
		for (HospitalDTO h :hospitals) {
			uri+=Integer.toString(i)+";";
			i++;
		}
		uri = uri.substring(0, uri.length() - 1);
		HttpResponse<JsonNode> response;
		
		try {
			//Table des évènements
			// Service qui traite et gère la 
			// Messaging Queue Service -3
			// RabbitMQ
			// Formulaire -> clique table d'évent
			// Test Unitaires JUnit -2
			// Tests Mockvmc 
			// CI CD -Pipline Jenkins GitLab -1 Unit + intégration
			response = Unirest.get(uri).asJson();
			JSONParser parser = new JSONParser();
			String content =response.getBody().toString();
			Object ob = parser.parse(content);
			JSONObject job = (JSONObject) ob;
			JSONArray ja =(JSONArray)((JSONArray)job.get("durations")).get(0);
			i = 0;
			for (Object s:ja) {
				HospitalDTO h = hospitals.get(i);
				float f = Float.parseFloat(s.toString());
				h.setDistance(f);
				i++;
			}
	    	hospitals.sort(Comparator.comparingDouble(HospitalDTO::getDistance));
			
		} catch (UnirestException e) {
			System.out.println("Error 1"+e.toString());
			e.printStackTrace();
		}
		
	    return hospitals;
	}
	
	
	
	@Transactional
	public Iterable<Hospital> getHospitals(){
		return HospitalRepository.findAll();
	}

}
