package com.ocal.medhead.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ocal.medhead.model.*;

@Repository
public interface PatientRepository extends CrudRepository<Patient,Long> {

}
