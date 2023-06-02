package com.ocal.medhead.controller;

import java.util.*;
import com.github.cliftonlabs.json_simple.*;
import com.ocal.medhead.model.*;
import com.ocal.medhead.repository.SpecialitiesRepository;
import com.ocal.medhead.service.AvailableBedService;
import com.ocal.medhead.service.BedFindingService;

import dto.Coordinates;
import dto.HospitalDTO;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import lombok.*;

//
@Data
@RestController
public class HospitalSolverControler {
    @Autowired
    private final BedFindingService bfs;
    @Autowired
    private final AvailableBedService abs;
    @Autowired
    private final SpecialitiesRepository rp;
    
    
    private float _distance(float tlat,float tlon, Hospital b) {
    	float temp1 = (b.getLongitude() - tlon);
    	temp1*=temp1;
    	float temp2 = (b.getLatitude() - tlat);
    	temp2*=temp2;
    	
    	return (float)temp1 + temp2;
    }
    
    public List<HospitalDTO> getHospitalsBySpec(
            float tlong,
             float tlat,
             long specId) {
    	List<HospitalDTO> hs =new LinkedList<>();
    	Specialities spec = rp.findById(specId).orElse(null);
    	if(rp == null ) {
    		return hs;
    	}
    	List<SpecialitiesHospital> sphos = bfs.getHospitalsBySpec(spec);
    	for(SpecialitiesHospital spho : sphos) {
    		Hospital h = spho.getHospital();
    		float f = _distance(tlat,tlong,h);
			int availableBeds = abs.availableBeds(h);
			if (availableBeds > 0) {
				hs.add(new HospitalDTO(f,h,availableBeds));
			}
    	}
    	hs.sort(Comparator.comparingDouble(HospitalDTO::getDistance));
        return hs.subList(0, 10);
    }
    
    public List<HospitalDTO> getHospitalsBySpecA(
           String address,
            long specId) {
    		Coordinates cd = new Coordinates(-999.0f,-999.0f);
			try {
				cd = bfs.getCoordinatesFromAddress(address);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		return this.getHospitalsBySpec(cd.getLongitude(),cd.getLatitude(),specId);
	}

    @GetMapping
    @RequestMapping("/hospitals")
    @ResponseBody
    public List<HospitalDTO> getHospitalsBySpecB(
            @RequestParam("address") String address,
            @RequestParam("spec_id") long specId) throws ParseException{
    	Coordinates cd = new Coordinates(-999.0f,-999.0f);
		try {
			cd = bfs.getCoordinatesFromAddress(address);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	List<HospitalDTO> hes = getHospitalsBySpec(cd.getLatitude(),cd.getLongitude(),specId);
    	return bfs.getClosestHospital(cd, hes);
    }
}
