package com.ocal.medhead.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ocal.medhead.repository.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RestController
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class SpecialitiesController {
	@Autowired
	SpecialitiesRepository sr;
	@Autowired 
	SpecGroupRepository sgr;

    @GetMapping("/Specialities")
    @ResponseBody
    public ResponseEntity<?> getSpecByGroup
    (@RequestParam("spec_group_id") long groupId){
		if (sgr.findById(groupId).isPresent()) {
			return ResponseEntity.ok(sr.findBySpecgroup_Id(groupId));
		}
		else {
			return ResponseEntity.badRequest().body("Wrong Group Id");
		}
    }
}
