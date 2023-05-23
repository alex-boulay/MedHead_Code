package com.OCAL.MedHead.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.OCAL.MedHead.model.Hospital;
import com.OCAL.MedHead.model.SpecialitiesHospital;
import org.springframework.stereotype.Repository;
@Repository
public interface SpecialitiesHospitalRepository extends CrudRepository<SpecialitiesHospital,Long> {
	 Iterable<SpecialitiesHospital> findBySpeciality_Id(Long specId);

}
