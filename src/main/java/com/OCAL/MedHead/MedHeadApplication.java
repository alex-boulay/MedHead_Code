package com.ocal.medhead;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@ComponentScan({"com.ocal.medhead","config"})
@SpringBootApplication(scanBasePackages={"com.OCAL.MedHead","com.OCAL.MedHead.service","com.OCAL.MedHead.controller","com.OCAL.MedHead.repository"})
@EntityScan("com.OCAL.MedHead.model")
@Getter
@Setter
//@NoArgsConstructor
public class MedHeadApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MedHeadApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("DONE INITIALIZING");	
	}

}

