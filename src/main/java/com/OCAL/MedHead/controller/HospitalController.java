package com.OCAL.MedHead.controller;


import java.util.List;
import com.OCAL.MedHead.model.*;
import com.OCAL.MedHead.service.BedFindingService;

import jakarta.transaction.Transactional;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/hospitals")
public class HospitalController {
    
    private final BedFindingService bfs;

    @Autowired
    public HospitalController(BedFindingService bfs) {
        this.bfs = bfs;
    }

    @GetMapping
    @ResponseBody
    public Hospital getfirstHospitals() {
        return bfs.getHospitals().iterator().next();
    }
    
    
}