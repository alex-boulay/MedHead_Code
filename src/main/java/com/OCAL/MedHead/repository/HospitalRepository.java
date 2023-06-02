package com.ocal.medhead.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ocal.medhead.model.Hospital;

@Repository
public interface HospitalRepository extends CrudRepository<Hospital,Long>{
}
