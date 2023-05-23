package com.OCAL.MedHead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;



import com.OCAL.MedHead.service.*;

import lombok.Data;

import com.OCAL.MedHead.repository.*;
import com.OCAL.MedHead.controller.BedSolverProxy;
import com.OCAL.MedHead.model.*;


@SpringBootApplication(scanBasePackages={"com.OCAL.MedHead","com.OCAL.MedHead.service","com.OCAL.MedHead.controller","com.OCAL.MedHead.repository"})
@EntityScan("com.OCAL.MedHead.model")
@Data
public class MedHeadApplication implements CommandLineRunner {

	@Autowired
	private BedSolverProxy bfs;
	
	public static void main(String[] args) {
		SpringApplication.run(MedHeadApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(bfs.getHospitalsBySpecB("Piccadilly Circus, London",1L));
	}

}

