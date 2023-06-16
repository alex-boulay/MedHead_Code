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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Service
public class BedFindingService {

	@Autowired
	private SpecialitiesRepository SpecialitiesRepository;
	@Autowired
	private SpecialitiesHospitalRepository SpecialitiesHospitalRepository;
	@Autowired 
	private AvailableBedService Abs;

	public Coordinates getCoordinatesFromAddress(String address) throws UnirestException {
		String uri = "http://nominatim.openstreetmap.org/search?q=";
		uri += address.replaceAll(" ","+");
		uri += "&format=json";

		float latitude = -999.0f;
		float longitude = -999.0f;
		
		HttpResponse<JsonNode> response= Unirest.get(uri).asJson();
		int statusCode = response.getStatus();
		String content =response.getBody().toString();
		if (statusCode >= 200 && statusCode <300) {
			//System.out.println(content);
		}
		else { 
			String badreq ="";
			if (statusCode >= 400 && statusCode <500) {
				badreq +=("Invalid Request");
			} else if (statusCode >= 500 && statusCode <=600) {
				badreq +=("Server Side Error");
			} else {
				badreq +=("Request Failed");
			}
		    throw new UnirestException(badreq);
		  }
		JSONParser parser = new JSONParser();
		try {
			Object ob = parser.parse(content);
			JSONArray js =(JSONArray) ob;
			if(js.size()==0) {
				throw new UnirestException("Nomination Failed to get Coordinates, Array Null");
			}
			JSONObject job =(JSONObject) js.get(0);
			if(job.get("lon") ==null || job.get("lat") ==null ) {
				throw new UnirestException("Nomination Failed to get Coordinates, Not latitude or Longitude");
			}
		    latitude = Float.parseFloat(job.get("lat").toString());
		    longitude = Float.parseFloat(job.get("lon").toString());
		}
		catch(ParseException e) {
			throw new UnirestException("Parsing Error From Nomination Address API");
		}
		Coordinates coord = new Coordinates();
		coord.setLatitude(latitude);
		coord.setLongitude(longitude);
		if (coord.areValid()) {
			return coord;
		}
		else {
			throw new UnirestException("Nomination Failed to get Coordinates");
		}
	}
	public List<HospitalDTO> getClosestHospital(Coordinates c,List<HospitalDTO> hospitals) throws UnirestException {
		
		// Parsing the Data to match OSRM API
		String uri = "http://router.project-osrm.org/table/v1/driving/";
		uri += c.longLat()+";";
		for (HospitalDTO h : hospitals) {
			uri+= Float.toString(h.getHospital().getLongitude())+","+Float.toString( h.getHospital().getLatitude())+";";
		}
		uri = uri.substring(0, uri.length() - 1);
		uri += "?sources=0&destinations=";
		int i ;
		for ( i = 1; i<hospitals.size() +1;i++ ) {
			uri+=Integer.toString(i)+";";
		}
		uri = uri.substring(0, uri.length() - 1);
		// Executing the request
		HttpResponse<JsonNode> response = Unirest.get(uri).asJson();
		int statusCode = response.getStatus();
		String content =response.getBody().toString();
		if (statusCode >= 200 && statusCode <300) {
		}
		else { 
			String badreq ="";
			if (statusCode >= 400 && statusCode <500) {
				badreq +=("Invalid Request, Verfiy ");
			} else if (statusCode >= 500 && statusCode <=600) {
				badreq +=("Server Side Error");
			} else {
				badreq +=("Request Failed");
			}
		    throw new UnirestException(badreq);
		  }
		// parsing data if it worked
		JSONParser parser = new JSONParser();
		try {
			Object ob = parser.parse(content);
			JSONObject job = (JSONObject) ob;
			if(job.get("durations")==null || ((JSONArray)job.get("durations")).get(0)==null ) {
				throw new UnirestException("Unable to get OSRM Array distances");
			}
			JSONArray ja =(JSONArray)((JSONArray)job.get("durations")).get(0);
			i = 0;
			for (Object s:ja) {
				HospitalDTO h = hospitals.get(i);
				float f = Float.parseFloat(s.toString());
				h.setDistance(f);
				i++;
			}
		}
		catch(ParseException e) {
			throw new UnirestException("Parsing Issue with OSRM API");
		}
		
		// sorting the hospital with their distance from the coordiantes
    	hospitals.sort(Comparator.comparingDouble(HospitalDTO::getDistance));
	    return hospitals;
	}
    
    public float _distance(Coordinates c, Hospital b) {
    	float temp1 = (b.getLongitude() - c.getLongitude());
    	temp1*=temp1;
    	float temp2 = (b.getLatitude() - c.getLatitude());
    	temp2*=temp2;
    	return (float)temp1 + temp2;
    }

    public List<HospitalDTO> getHospitalsBySpecS(Coordinates c,
             long specId) {
    	List<HospitalDTO> hs =new LinkedList<>();
    	Specialities spec = SpecialitiesRepository.findById(specId).orElse(null);
    	if(spec == null ) {
    		return hs;
    	}
    	List<SpecialitiesHospital> sphos = SpecialitiesHospitalRepository.findById_Speciality(spec);
    	for(SpecialitiesHospital spho : sphos) {
    		Hospital h = spho.getHospital();
    		float f = _distance(c,h);
			int availableBeds = Abs.availableBeds(h);
			if (availableBeds > 0) {
				hs.add(new HospitalDTO(f,h,availableBeds));
			}
    	}
    	hs.sort(Comparator.comparingDouble(HospitalDTO::getDistance));
    	if(hs.size()>10) {
    		return hs.subList(0, 10);
    	}
    	return hs;
    }
}
