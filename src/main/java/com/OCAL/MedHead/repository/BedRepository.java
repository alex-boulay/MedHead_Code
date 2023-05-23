package com.OCAL.MedHead.repository;

import com.OCAL.MedHead.model.Bed;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
@Repository
public interface BedRepository extends CrudRepository<Bed,Long>{
	
}
