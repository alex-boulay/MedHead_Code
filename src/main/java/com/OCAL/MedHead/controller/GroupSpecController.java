package com.ocal.medhead.controller;

import java.util.*;

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

import com.ocal.medhead.model.*;
import com.ocal.medhead.repository.SpecGroupRepository;

import lombok.Data;

@RestController
@RequestMapping("/SpecGroup")
@Data
public class GroupSpecController {
	@Autowired
	SpecGroupRepository sgr;
    @GetMapping
    @ResponseBody
    public Iterable<SpecGroup> getGroupSpec(){
    	return sgr.findAll();
    }
}
