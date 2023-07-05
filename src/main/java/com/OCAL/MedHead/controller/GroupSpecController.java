package com.ocal.medhead.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ocal.medhead.model.*;
import com.ocal.medhead.repository.SpecGroupRepository;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@CrossOrigin(origins = "*")
@RestController
@NoArgsConstructor
public class GroupSpecController {
	@Autowired
	SpecGroupRepository sgr;

    @GetMapping("/specgroup")
    @ResponseBody
    public Iterable<SpecGroup> getGroupSpec(){
    	return sgr.findAll();
    }
}
