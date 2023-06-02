package com.ocal.medhead.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ocal.medhead.model.Specialities;
import com.ocal.medhead.model.SpecialitiesHospital;
import com.ocal.medhead.model.SpecialitiesHospitalId;
@Repository
public interface SpecialitiesHospitalRepository extends CrudRepository<SpecialitiesHospital,SpecialitiesHospitalId> {
	 List<SpecialitiesHospital> findById_Speciality(Specialities spec);
}
