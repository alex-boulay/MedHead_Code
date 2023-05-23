package com.OCAL.MedHead.repository;

import org.springframework.data.repository.CrudRepository;
import com.OCAL.MedHead.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient,Long> {

}
