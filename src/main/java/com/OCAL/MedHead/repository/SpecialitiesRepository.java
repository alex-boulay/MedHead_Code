package com.ocal.medhead.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ocal.medhead.model.Specialities;

@Repository
public interface SpecialitiesRepository extends CrudRepository<Specialities,Long> {
	 List<Specialities> findBySpecgroup_Id(long specgroupId);
}
