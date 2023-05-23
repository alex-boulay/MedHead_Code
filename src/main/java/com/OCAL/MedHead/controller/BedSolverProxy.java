package com.OCAL.MedHead.controller;

import java.util.*;
import com.github.cliftonlabs.json_simple.*;

import dto.Coordinates;
import dto.HospitalEx;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.OCAL.MedHead.model.*;
import com.OCAL.MedHead.service.BedFindingService;

import lombok.*;

//
@Data
@RestController
public class BedSolverProxy {
    @Autowired
    private final BedFindingService bfs;

    private float _distance(float tlat,float tlon, Hospital b) {
    	float temp1 = (b.getLongitude() - tlon);
    	temp1*=temp1;
    	float temp2 = (b.getLatitude() - tlat);
    	temp2*=temp2;
    	
    	return (float)Math.sqrt(temp1 + temp2);
    }
    
    private boolean compfunction(double a,double b) {
    	return a>b;
    }
    
    @GetMapping
    @RequestMapping("/ah")
    @ResponseBody
    public List<HospitalEx> getHospitalsBySpec(
            @RequestParam("longitude") float tlong,
            @RequestParam("latitude") float tlat,
            @RequestParam("spec_id") long specId) {
    	Iterable<SpecialitiesHospital> sphos = bfs.getHospitalsBySpec(specId);
    	List<HospitalEx> hs =new LinkedList<>();
    	for(SpecialitiesHospital spho : sphos) {
    		Hospital h = spho.getHospital();
    		float f = _distance(tlat,tlong,h);
    		hs.add(new HospitalEx(f,h));
    	}
    	hs.sort(Comparator.comparingDouble(HospitalEx::getDistance));
        return hs;
    }
    @GetMapping
    @RequestMapping("/ah2")
    @ResponseBody
    public List<HospitalEx> getHospitalsBySpecA(
            @RequestParam("address") String address,
            @RequestParam("spec_id") long specId) {
    		Coordinates cd = new Coordinates(-999.0f,-999.0f);
			try {
				cd = bfs.getCoordinatesFromAddress(address);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		return this.getHospitalsBySpec(cd.getLongitude(),cd.getLatitude(),specId);
	}

    @GetMapping
    @RequestMapping("/ah3")
    @ResponseBody
    public List<HospitalEx> getHospitalsBySpecB(
            @RequestParam("address") String address,
            @RequestParam("spec_id") long specId) throws ParseException{
    	Coordinates cd = new Coordinates(-999.0f,-999.0f);
		try {
			cd = bfs.getCoordinatesFromAddress(address);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	List<HospitalEx> hes = getHospitalsBySpec(cd.getLatitude(),cd.getLongitude(),specId);
    	List<Hospital> hs = new ArrayList<Hospital>();
    	int i = 0;
    	for(HospitalEx he : hes) {
    		hs.add(he.getHospital());
    		i++;
    		if(i > 4) {
    			break;
    		}
    	}
    	return bfs.getClosestHospital(cd, hs);
    }
}
