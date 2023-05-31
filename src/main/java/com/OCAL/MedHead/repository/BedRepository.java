package com.ocal.medhead.repository;

import java.util.List;
import com.ocal.medhead.model.*;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.ocal.medhead.model.Bed;
@Repository
public interface BedRepository extends CrudRepository<Bed,Long>{
	List<Bed> findByHospital(Hospital h);
}
