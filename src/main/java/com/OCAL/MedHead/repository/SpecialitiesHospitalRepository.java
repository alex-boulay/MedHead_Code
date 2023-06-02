package com.ocal.medhead.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ocal.medhead.model.SpecialitiesHospital;
@Repository
public interface SpecialitiesHospitalRepository extends CrudRepository<SpecialitiesHospital,Long> {
	 List<SpecialitiesHospital> findBySpeciality_Id(Long specId);
}
