package com.OCAL.MedHead.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;


import com.OCAL.MedHead.model.Hospital;

import lombok.Data;

import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends CrudRepository<Hospital,Long>{
}
