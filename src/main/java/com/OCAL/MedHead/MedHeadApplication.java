package com.ocal.medhead;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.ocal.medhead.controller.HospitalSolverControler;
import com.ocal.medhead.model.*;
import com.ocal.medhead.repository.*;
import com.ocal.medhead.service.*;

import lombok.Data;


@SpringBootApplication(scanBasePackages={"com.OCAL.MedHead","com.OCAL.MedHead.service","com.OCAL.MedHead.controller","com.OCAL.MedHead.repository"})
@EntityScan("com.OCAL.MedHead.model")
@Data
public class MedHeadApplication implements CommandLineRunner {

	@Autowired
	private HospitalSolverControler bfs;
	@Autowired
	private AvailableBedService abs;
	@Autowired
	private HospitalRepository hr;
	
	public static void main(String[] args) {
		SpringApplication.run(MedHeadApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(bfs);
		System.out.println(abs);
		Optional<Hospital> oh= hr.findById(1L);
		if (oh.isPresent()) {
			System.out.println(abs.availableBeds(oh.get()));
		}
	}
}

