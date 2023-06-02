package com.ocal.medhead.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ocal.medhead.model.*;
import com.ocal.medhead.repository.*;

import lombok.Data;

@RestController
@RequestMapping("/Specialities")
@Data
public class SpecialitiesController {
	@Autowired
	SpecialitiesRepository sr;
    @GetMapping
    @ResponseBody
    public List<Specialities> getSpecByGroup
    (@RequestParam("spec_group_id") long groupId){
    	return sr.findBySpecgroup_Id(groupId);
    }
}
