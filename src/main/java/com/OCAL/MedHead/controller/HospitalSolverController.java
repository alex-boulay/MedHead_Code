package com.ocal.medhead.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.ocal.medhead.service.BedFindingService;
import dto.Coordinates;
import dto.HospitalDTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class HospitalSolverController {
    @Autowired
    private BedFindingService bfs;
    
    //find service
    @GetMapping("/hospitals")
    @ResponseBody
    public ResponseEntity<?> getHospitalsBySpecB(
            @RequestParam("address") String address,
            @RequestParam("spec_id") long specId) throws UnirestException{
    	Coordinates cd = new Coordinates(-999.0f,-999.0f);
		try {
			cd = bfs.getCoordinatesFromAddress(address);
		} catch (UnirestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    	List<HospitalDTO> hes = bfs.getHospitalsBySpecS(cd,specId);
    	try {
        	return ResponseEntity.ok(bfs.getClosestHospital(cd, hes));
    	}
    	catch(UnirestException e) {
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }
}
