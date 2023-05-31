package com.ocal.medhead.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ocal.medhead.model.Bed;
import com.ocal.medhead.model.BedOccupation;

@Repository
public interface BedOccupationRepository extends CrudRepository<BedOccupation,Long>{
	List<BedOccupation> findByBed(Bed b);
}
