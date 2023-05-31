package com.ocal.medhead.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;

import lombok.Data;

import org.springframework.stereotype.Repository;

import com.ocal.medhead.model.Hospital;

@Repository
public interface HospitalRepository extends CrudRepository<Hospital,Long>{
}
