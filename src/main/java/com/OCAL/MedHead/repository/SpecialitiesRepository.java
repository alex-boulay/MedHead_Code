package com.OCAL.MedHead.repository;



import org.springframework.data.repository.CrudRepository;
import com.OCAL.MedHead.model.Specialities;

import org.springframework.stereotype.Repository;

@Repository
public interface SpecialitiesRepository extends CrudRepository<Specialities,Long> {
	 Iterable<Specialities> findBySpecgroup_Id(long specgroupId);
}
